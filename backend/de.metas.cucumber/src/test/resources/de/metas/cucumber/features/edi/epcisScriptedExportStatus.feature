@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI_DESADV_InOut_Link
@ghActions:run_on_executor7
Feature: EPCIS scripted-export status — success, error and re-send flows
## Verifies the ExternalSystem_ScriptedExportConversion_Status lifecycle for the EPCIS export config:
## (a) shipment completed → invocation enqueued → /ok callback → status row Sent + roll-up M_InOut.EPCIS_ExportStatus=Sent
## (b) same path but /error callback → status row Error + AD_Issue_ID linked + roll-up Error
## (c) re-send of an errored shipment → a NEW Pending+IsResend=Y attempt row (per-attempt history, prior attempt kept) → Sent via /ok

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
  Scenario: S30088_030 — re-send of an errored shipment adds a NEW attempt row (per-attempt history), keeping the errored one

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

    # The re-send inserts a NEW attempt row (IsResend=Y), reaching at minimum Enqueued — the errored
    # first attempt is kept, so this is the LATEST row (newest-first), not a mutation of the old one.
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

    # Per-attempt history: the status tab's grid now shows TWO attempt rows (newest-first), each with
    # its own data — the successful re-send on top (Sent, IsResend=Y, HTTP 200, no issue) and the
    # errored first attempt beneath it (Error, IsResend=N, its AD_Issue retained). (Under the former
    # single-row upsert there would be a single row.)
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend | HttpResponseCode | HasAD_Issue |
      | io_030     | scriptedCfg_es                                    | S            | Y        | 200              | N           |
      | io_030     | scriptedCfg_es                                    | E            | N        |                  | Y           |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30088_050
  Scenario: S30088_050 — Change-EPCIS-Export-Status action writes a new audited DontSend attempt and releases the in-flight reverse block
    ## An operator uses the shipment "Change EPCIS Export Status" action to mark a stuck in-flight export as
    ## DontSend. This writes a NEW, process-instance-stamped attempt row (who/when audit; the prior in-flight
    ## attempt is kept as history). Because the shipment's LATEST EPCIS status is then no longer in-flight, the
    ## reverse/reactivate guard that was blocking the shipment (in-flight → SSCC may already be at the receiver)
    ## is released and the shipment can be reversed.

    # EPCIS-classified scripted-export config: its outbound-data process IS the EPCIS export
    # (M_InOut_EPCIS_Export_JSON), so EpcisExportConfigMatcher classifies it as an EPCIS config — which
    # both the reverse guard and the Change-EPCIS-Export-Status service require. No restrictive WhereClause
    # → the export enqueues on any completion (reaches in-flight U). Overrides the Background's non-EPCIS
    # scriptedCfg_es for M_InOut (table-scoped takeover).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | esConfig_050             | scriptedCfg_050                                   | M_InOut_EPCIS_Export_JSON        | M_InOut   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference          |
      | o_050      | true    | bp_es         | 2026-06-09  | PO_S30088_050_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_050     | o_050      | product_es   | 10         | pip_es                  |
    When the order identified by o_050 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_050     | ol_050         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_050                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_050                | io_050     |

    # Drain async material/DESADV workpackages from shipment generation (avoid spilling into sibling executor tests).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Shipment completion enqueues the EPCIS export → the attempt row is Enqueued (U, in-flight)
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_050     | scriptedCfg_050                                   | U            |

    # In-flight → reversing the shipment is blocked (re-completing could re-transmit an SSCC already at the receiver)
    And the shipment identified by io_050 is reversed expecting error
      | AD_Message_ID |
      |               |

    # Operator marks it DontSend via the shipment "Change EPCIS Export Status" action
    When Change EPCIS Export Status process is run for shipment io_050 with target status DontSend

    # A NEW attempt row (DontSend=N) is now the latest, stamped with the process AD_PInstance_ID (who/when audit);
    # the prior in-flight (U) attempt is retained as history (per-attempt history, newest-first).
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | HasAD_PInstance |
      | io_050     | scriptedCfg_050                                   | N            | Y               |
      | io_050     | scriptedCfg_050                                   | U            |                 |

    # Latest EPCIS status is no longer in-flight → the reverse guard releases; the shipment can now be reversed.
    And the shipment identified by io_050 is reversed


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30088_060
  Scenario: S30088_060 — Re-send picks up a shipment parked in Pending (a resting Pending IS resendable)
    ## An operator resets a stuck in-flight EPCIS export to "Not yet sent" (Pending) via the Change EPCIS
    ## Export Status action, then runs Re-send. An operator-parked Pending is not-in-flight (nothing is
    ## being sent) and is stamped with the process instance, so Re-send picks it up as the first send —
    ## not a double-send.

    # EPCIS-classified config (its outbound-data process IS the EPCIS export) so the Change EPCIS Export
    # Status action applies; no WhereClause → the export enqueues on any completion (reaches in-flight U).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | esConfig_060             | scriptedCfg_060                                   | M_InOut_EPCIS_Export_JSON        | M_InOut   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference          |
      | o_060      | true    | bp_es         | 2026-06-09  | PO_S30088_060_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_060     | o_060      | product_es   | 10         | pip_es                  |
    When the order identified by o_060 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_060     | ol_060         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_060                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_060                | io_060     |

    # Drain async material/DESADV workpackages from shipment generation.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Completion enqueues the EPCIS export → in-flight (U)
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_060     | scriptedCfg_060                                   | U            |

    # Operator resets it to "Not yet sent" (Pending) via the Change EPCIS Export Status action → a NEW
    # process-instance-stamped attempt row; the prior in-flight (U) attempt is kept as history.
    When Change EPCIS Export Status process is run for shipment io_060 with target status Pending
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | HasAD_PInstance |
      | io_060     | scriptedCfg_060                                   | P            | Y               |

    # Re-send picks up the operator-parked Pending shipment → a NEW attempt row (IsResend=Y) reaching at
    # least Enqueued.
    When M_InOut_ReSend_ScriptedExportConversion process is run for shipment io_060
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
      | io_060     | scriptedCfg_060                                   | U            | Y        |

    # Simulate /ok → the resend attempt reaches Sent.
    When the scripted-export /ok callback is posted for shipment io_060 with HTTP code 200
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
      | io_060     | scriptedCfg_060                                   | S            | Y        |
    And after not more than 10s, M_InOut EPCIS_ExportStatus is:
      | M_InOut_ID | EPCIS_ExportStatus |
      | io_060     | S                  |


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


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30916_100
  Scenario: S30916_100 — successful EPCIS send records the transmitted SSCCs in the ledger, making the next selection a no-op
  ## Drives the real success path end-to-end for a config whose outbound-data process IS the EPCIS
  ## export (de.metas.edi.process.export.json.M_InOut_EPCIS_Export_JSON) and whose WhereClause is the
  ## production outbound-selection gate ("de.metas.edi".epcis_has_events(m_inout_id)): a single order
  ## produces ONE standalone, fully-covered LU (one physical pallet, one SSCC18); the shipment
  ## completion enqueues the export; the /ok callback simulates the external system confirming
  ## receipt. EDI_EPCIS_Transmitted_SSCC must then carry a ledger row for that SSCC18 (the success
  ## listener records it as the side-effect of a confirmed send). Once the ledger holds that row,
  ## epcis_has_events(...) for the SAME shipment must flip to false: the real outbound-selection
  ## WHERE-clause would no longer match this shipment, so a re-trigger of the same selection could
  ## not re-send it (exactly-once).

    # Own product/pricing/BPartner — independent of the Background's product_es/bp_es.
    And metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_100 | 4060000001000 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_100 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_100 | ps_S30916_100      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_100 | pl_S30916_100  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_100         | p_S30916_100  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient (get_epcis_events_json_fn reads pack info via EDI_Desadv_Pack)
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_100 | Y          | ps_S30916_100      | 9900000310000 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                 |
      | bp_S30916_100 | true                 | 9900000310000         | edi_setting_S30916_100_bp  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID  |
      | bp_S30916_100 | p_S30916_100  |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID         |
      | pi_LU_S30916_100   |
      | pi_TU_S30916_100   |
      | pi_VHU_S30916_100  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID         | HU_UnitType | IsCurrent |
      | piv_LU_S30916_100   | pi_LU_S30916_100   | LU          | Y         |
      | piv_TU_S30916_100   | pi_TU_S30916_100   | TU          | Y         |
      | piv_VHU_S30916_100  | pi_VHU_S30916_100  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID    | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID  |
      | pii_LU_S30916_100  | piv_LU_S30916_100   | 20  | HU       | pi_TU_S30916_100    |
      | pii_TU_S30916_100  | piv_TU_S30916_100   | 0   | MI       |                     |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID  | M_Attribute.Value |
      | piv_LU_S30916_100   | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID    | M_Product_ID  | Qty | ValidFrom  |
      | pip_S30916_100          | pii_TU_S30916_100  | p_S30916_100  | 10  | 2020-01-01 |

    # Scripted-export config whose outbound process IS the EPCIS export and whose WhereClause is the
    # real production outbound-selection gate. Overrides the Background's always-true scriptedCfg_es
    # for M_InOut (table-scoped takeover — see saveExternalSystemConfigWithScriptedExportConversion).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_S30916_100      | scriptedCfg_S30916_100                            | M_InOut_EPCIS_Export_JSON        | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    # Sales order: 10 PCE = 1 TU, standalone pallet
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_100  | true    | bp_S30916_100 | 2026-06-10  | 1300000010  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID   | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_100  | o_S30916_100 | p_S30916_100  | 10         | pip_S30916_100          |

    When the order identified by o_S30916_100 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S30916_100             | bp_S30916_100             | o_S30916_100           | P                |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_100  | ol_S30916_100  | N             |

    # ─── Inventory → CU → TU → LU → SSCC18 ──────────────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_100            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_100            | invLine_S30916_100            | p_S30916_100            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_100'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_100            | cu_S30916_100      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_100        | 10    | pip_S30916_100                     | tu_S30916_100                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_100        | 1     | pii_LU_S30916_100           | lu_S30916_100             |

    And M_HU_Attribute is changed
      | M_HU_ID        | M_Attribute_ID.Value | Value              |
      | lu_S30916_100  | SSCC18               | 987654321000031000 |

    # ─── TU-level picking ─────────────────────────────────────────────────────────────
    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_100        | ss_S30916_100                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_100        | ss_S30916_100                    |

    # ─── Generate picked shipment as DRAFT first (QuantityType=PD), THEN complete it as a
    # separate step — mirrors the two-step generate-then-complete pattern S30558_040 already uses
    # successfully for this same "de.metas.edi".epcis_has_events(m_inout_id) WhereClause. Combining
    # generate+complete in one call (IsCompleteShipments=true) races the shipment's own
    # AFTER_COMPLETE WhereClause evaluation against the async processing that the completion itself
    # triggers (observed locally: the combined call evaluates epcis_has_events too early and records
    # DontSend instead of Enqueued). EDI_Desadv_Pack rows are themselves only created once the
    # shipment is completed, so that assertion is checked after the completion step below, not before.
    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_100         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ss_S30916_100         | io_S30916_100  |

    # Clear the transmission ledger BEFORE completing the shipment. Completion fires the after-commit
    # scripted-export selection, which evaluates "de.metas.edi".epcis_has_events(m_inout_id) — and that
    # gate excludes any SSCC already in the (real, non-rolled-back) ledger. A row left by a PREVIOUS run
    # of this scenario (same SSCC18) would otherwise make the gate return false AT COMPLETION and record
    # DontSend instead of Enqueued. The local provided-infra DB is not reset between runs, so the clear
    # must precede the completion — clearing only before the baseline assertion below is too late.
    And the EPCIS transmission ledger is empty

    And the shipment identified by io_S30916_100 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | pack_S30916_100    | d_S30916_100             | false               |

    # ─── Baseline sanity: the shipment IS export-relevant (ledger was cleared before completion) ──
    Then the EPCIS export-relevance for M_InOut identified by io_S30916_100 is true

    # ─── Drive the real success path: shipment completion enqueues the EPCIS export ────
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_100 | scriptedCfg_S30916_100                             | U            |

    When the scripted-export /ok callback is posted for shipment io_S30916_100 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_100 | scriptedCfg_S30916_100                             | S            |

    # ─── CORE ASSERTION: the successful send recorded the transmitted SSCC in the ledger ──
    Then after not more than 10s, EDI_EPCIS_Transmitted_SSCC is found:
      | SSCC18              | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031000  | scriptedCfg_S30916_100                             | io_S30916_100 |

    # ─── Idempotency proof: the real outbound-selection gate now excludes the shipment ────
    # Once the ledger holds the SSCC, "de.metas.edi".epcis_has_events(...) — the exact WHERE-clause
    # this config's WhereClause evaluates — must flip to false: a re-trigger of the same outbound
    # selection would no longer match this shipment, so it would not be re-sent.
    And the EPCIS export-relevance for M_InOut identified by io_S30916_100 is false


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30916_110
  Scenario: S30916_110 — a shipment whose EPCIS SSCCs were transmitted cannot be reversed/reactivated/voided
  ## Reversal-recreate is the duplicate-transmission root cause this guard closes: once a shipment's
  ## EPCIS SSCC events were transmitted to the receiver (an ACTIVE EDI_EPCIS_Transmitted_SSCC ledger
  ## row exists for it), reversing/reactivating/voiding it — which would recreate the document and
  ## re-run shipment completion — must be rejected, or the same physical SSCC would be re-transmitted
  ## at the receiver. Deactivating the ledger row (the WebUI shipment-tab feature) is the sanctioned
  ## way to unblock it, consistent with it also unblocking re-sending the SSCC (see S30916_040).
  ## Control: a plain shipment with no transmitted SSCC reverses normally — already covered by the
  ## existing materialDispo/reverseShipment.feature scenario, which this guard leaves unaffected
  ## (no ledger row for that shipment, so hasActiveTransmittedForInOut is false).

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference          |
      | o_110      | true    | bp_es         | 2026-06-09  | PO_S30916_110_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_110     | o_110      | product_es   | 10         | pip_es                  |
    When the order identified by o_110 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_110     | ol_110         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_110                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_110                | io_110     |

    # Drain async material/DESADV workpackages from shipment generation so they don't spill into
    # the next scenario in this executor.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Simulate a prior successful EPCIS send: an ACTIVE ledger row for this shipment's physical SSCC.
    And metasfresh contains EDI_EPCIS_Transmitted_SSCC:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
      | 987654321000031100 | scriptedCfg_es                                     | io_110     |

    # ─── Reversing/reactivating/voiding a shipment whose EPCIS SSCCs were already transmitted must
    # be rejected — reverse-and-recreate would re-run shipment completion and re-transmit the same
    # physical SSCC (the Q2 duplicate-transmission root cause).
    And the shipment identified by io_110 is reversed expecting error
      | AD_Message_ID |
      |               |
    And the shipment identified by io_110 is reactivated expecting error
      | AD_Message_ID |
      |               |
    And the shipment identified by io_110 is voided expecting error
      | AD_Message_ID |
      |               |

    # ─── Unblock: deactivating the ledger row (the WebUI shipment-tab feature) lifts the guard ────
    And EDI_EPCIS_Transmitted_SSCC records are deactivated:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
      | 987654321000031100 | scriptedCfg_es                                     | io_110     |

    And the shipment identified by io_110 is reversed


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @ghActions:run_on_executor7
  @Id:S30916_120
  Scenario: S30916_120 — no-orphan (Q1) end-to-end: a shipment's standalone pallet is SENT at its own completion even though a shared pallet on it is still open; each physical SSCC transmitted exactly once
  ## End-to-end send-path proof of the per-LU close-gate (reproduces incident LS 1210901).
  ## ioA physically touches TWO LUs: luStandalone_120 (carries ONLY order A's crates, self-covered by
  ## ioA) and luShared_120 (carries crates from BOTH order A and order B). Order B's shipment (ioB)
  ## stays a DRAFT while ioA is completed, so luShared is still OPEN at ioA's completion.
  ##
  ## Under the OLD all-or-nothing gate, ioA had an uncovered TU (luShared's B-portion) → the whole
  ## shipment returned '{}' → epcis_has_events(ioA)=false → the scripted export recorded DontSend and
  ## luStandalone was ORPHANED (never transmitted). With the per-LU close-gate, ioA's completion sees
  ## luStandalone fully covered → epcis_has_events(ioA)=true → export Enqueued → /ok → Sent, and the
  ## success listener records ONLY luStandalone's SSCC (luShared is excluded, still open). When ioB
  ## (the closer of luShared) completes, luShared becomes fully covered → ioB is the one that
  ## transmits it. Net: each physical SSCC is transmitted EXACTLY ONCE, by exactly one shipment.

    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And the EPCIS transmission ledger is empty

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot_120   | 320.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_120 | 4060000001200 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_120 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_120 | ps_S30916_120      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_120 | pl_S30916_120  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_120         | p_S30916_120 | 5.0      | PCE      | Normal           |

    # AllowConsolidateInOut=N so the two orders' shipments stay separate M_InOuts (no consolidation)
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_S30916_120 | Y          | ps_S30916_120      | 9900000312000 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S30916_120 | 2900000312000 | bp_S30916_120 | true                | true         |

    # Scripted-export config whose outbound process IS the EPCIS export and whose WhereClause is the
    # real production outbound-selection gate. Overrides the Background's always-true config (table-scoped takeover).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_S30916_120      | scriptedCfg_S30916_120                            | M_InOut_EPCIS_Export_JSON        | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | Identifier                |
      | bp_S30916_120 | true                 | 9900000312000         | E                    | esConfig_S30916_120                | edi_setting_S30916_120_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_120 | p_S30916_120 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S30916_120 |
      | pi_TU_S30916_120 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S30916_120  | pi_LU_S30916_120 | LU          | Y         |
      | piv_TU_S30916_120  | pi_TU_S30916_120 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_120 | piv_LU_S30916_120  | 20  | HU       | pi_TU_S30916_120  |
      | pii_TU_S30916_120 | piv_TU_S30916_120  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_120  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_120          | pii_TU_S30916_120 | p_S30916_120 | 10  | 2020-01-01 |

    # Mobile UI picking profile — DO_NOT_CREATE (no auto-shipment); IsAllowCompletingPartialPickingJob=Y
    # so order A's single schedule is picked across TWO jobs (partial → standalone LU, complete → shared LU).
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs standalone-A + 5 TUs shared-A + 5 TUs shared-B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S30916_120 | 2026-06-09   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_120 | invLine_S30916_120 | p_S30916_120 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_120'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID               |
      | invLine_S30916_120 | pickFromCU_S30916_120 |

    And transform CU to new LU
      | sourceCU              | newLU                       | TU_PI_ID         | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S30916_120 | pickFromAggregatedLU_S30916 | pi_TU_S30916_120 | 10          | 15          |

    # Order A — 100 PCE → 10 TUs, split 5/5 across the standalone and the shared LU.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S30916_120 | true    | bp_S30916_120 | 2026-06-09  | 3120000001  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S30916_120 | oA_S30916_120 | p_S30916_120 | 100        | pip_S30916_120          |

    When the order identified by oA_S30916_120 is completed

    # Order B — 50 PCE → 5 TUs, joins the shared LU only.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S30916_120 | true    | bp_S30916_120 | 2026-06-09  | 3120000002  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S30916_120 | oB_S30916_120 | p_S30916_120 | 50         | pip_S30916_120          |

    When the order identified by oB_S30916_120 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S30916_120 | olA_S30916_120 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssB_S30916_120 | olB_S30916_120 | N             |

    # ─── Picking job 1: order A, PARTIAL — 5 TUs → brand-new standalone LU ───────────
    And start picking job for sales order identified by oA_S30916_120
    And scan picking slot identified by slot_120
    And set picking target as new LU identified by pi_LU_S30916_120
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_120          | pickFromAggregatedLU_S30916 | 5         |
    And expect current picking target
      | Existing_LU              |
      | luStandalone_S30916_120  |
    And complete picking job

    # ─── Picking job 2: order A, completes the schedule — remaining 5 TUs → brand-new shared LU ─
    And start picking job for sales order identified by oA_S30916_120
    And scan picking slot identified by slot_120
    And set picking target as new LU identified by pi_LU_S30916_120
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_120          | pickFromAggregatedLU_S30916 | 5         |
    And expect current picking target
      | Existing_LU           |
      | luShared_S30916_120   |
    And complete picking job

    # ─── Picking job 3: order B joins the SAME shared LU (LUPickingTarget.ofExistingHU) ──
    And start picking job for sales order identified by oB_S30916_120
    And scan picking slot identified by slot_120
    And set picking target as existing LU identified by luShared_S30916_120
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_120          | pickFromAggregatedLU_S30916 | 5         |
    And complete picking job

    # ─── Stamp distinct SSCC18 values on each physical LU ────────────────────────────
    And M_HU_Attribute is changed
      | M_HU_ID                  | M_Attribute_ID.Value | Value              |
      | luStandalone_S30916_120  | SSCC18               | 987654321000031200 |
    And M_HU_Attribute is changed
      | M_HU_ID                | M_Attribute_ID.Value | Value              |
      | luShared_S30916_120    | SSCC18               | 987654321000031201 |

    # ─── Both-drafts flow: generate DRAFT for ssA, then separately for ssB ─────────────
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S30916_120        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssA_S30916_120        | ioA_S30916_120 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssB_S30916_120        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssB_S30916_120        | ioB_S30916_120 |

    # ─── Complete ioA — touches luStandalone (self-covered) + luShared (still open, ioB is a draft) ─
    # No-orphan: luStandalone is fully covered → epcis_has_events(ioA)=true → export Enqueued (NOT DontSend).
    And the shipment identified by ioA_S30916_120 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID     | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioA_S30916_120 | scriptedCfg_S30916_120                            | U            |

    When the scripted-export /ok callback is posted for shipment ioA_S30916_120 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID     | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioA_S30916_120 | scriptedCfg_S30916_120                            | S            |

    # ioA's send recorded ONLY luStandalone's SSCC — luShared is still open, so it is NOT transmitted yet.
    And after not more than 10s, EDI_EPCIS_Transmitted_SSCC is found:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID     |
      | 987654321000031200 | scriptedCfg_S30916_120                            | ioA_S30916_120 |
    Then the EPCIS transmission ledger contains exactly:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID     |
      | 987654321000031200 | scriptedCfg_S30916_120                            | ioA_S30916_120 |

    # ─── Complete ioB — the CLOSER of luShared; all its TUs are now on completed shipments ──
    And the shipment identified by ioB_S30916_120 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID     | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioB_S30916_120 | scriptedCfg_S30916_120                            | U            |

    When the scripted-export /ok callback is posted for shipment ioB_S30916_120 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID     | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioB_S30916_120 | scriptedCfg_S30916_120                            | S            |

    # ioB (the closer) transmitted luShared's SSCC. Net ledger: each physical SSCC exactly once, by
    # exactly one shipment — luStandalone by ioA (at its own completion), luShared by ioB (the closer).
    And after not more than 10s, EDI_EPCIS_Transmitted_SSCC is found:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID     |
      | 987654321000031201 | scriptedCfg_S30916_120                            | ioB_S30916_120 |
    Then the EPCIS transmission ledger contains exactly:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID     |
      | 987654321000031200 | scriptedCfg_S30916_120                            | ioA_S30916_120 |
      | 987654321000031201 | scriptedCfg_S30916_120                            | ioB_S30916_120 |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @ghActions:run_on_executor7
  @Id:S30916_130
  Scenario: S30916_130 — no-duplicate (Q2) end-to-end: re-sending an already-transmitted shipment does NOT transmit its SSCC a second time
  ## End-to-end send-path proof of the ledger-exclusion (reproduces incident LS 1210886, where the
  ## same physical SSCC was transmitted twice). A standalone shipment is completed, exported and
  ## confirmed (/ok) → Sent, and the success listener records its SSCC in the ledger. Then the real
  ## production "Re-send" action (M_InOut_ReSend_ScriptedExportConversion) is invoked — exactly what
  ## support would do to re-transmit LS 1210886. Because the SSCC is now in the ledger,
  ## get_epcis_events_json_fn excludes it (epcis_has_events flips to false), so the re-send transmits
  ## nothing and the ledger still holds EXACTLY ONE row for that SSCC — no duplicate at the receiver.

    And the EPCIS transmission ledger is empty

    And metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_130 | 4060000001300 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_130 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_130 | ps_S30916_130      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_130 | pl_S30916_130  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_130         | p_S30916_130 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_130 | Y          | ps_S30916_130      | 9900000313000 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_130 | true                 | 9900000313000         | edi_setting_S30916_130_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_130 | p_S30916_130 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S30916_130  |
      | pi_TU_S30916_130  |
      | pi_VHU_S30916_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S30916_130  | pi_LU_S30916_130  | LU          | Y         |
      | piv_TU_S30916_130  | pi_TU_S30916_130  | TU          | Y         |
      | piv_VHU_S30916_130 | pi_VHU_S30916_130 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_130 | piv_LU_S30916_130  | 20  | HU       | pi_TU_S30916_130  |
      | pii_TU_S30916_130 | piv_TU_S30916_130  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_130  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_130          | pii_TU_S30916_130 | p_S30916_130 | 10  | 2020-01-01 |

    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_S30916_130      | scriptedCfg_S30916_130                            | M_InOut_EPCIS_Export_JSON        | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    # Sales order: 10 PCE = 1 TU, one standalone pallet
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_130 | true    | bp_S30916_130 | 2026-06-10  | 1300000013  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_130 | o_S30916_130 | p_S30916_130 | 10         | pip_S30916_130          |

    When the order identified by o_S30916_130 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_130 | ol_S30916_130  | N             |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_130            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_130            | invLine_S30916_130            | p_S30916_130            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_130'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_130            | cu_S30916_130      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_130       | 10    | pip_S30916_130                     | tu_S30916_130                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_130       | 1     | pii_LU_S30916_130          | lu_S30916_130             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30916_130 | SSCC18               | 987654321000031300 |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_130      | ss_S30916_130                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_130      | ss_S30916_130                    |

    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_130         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30916_130         | io_S30916_130 |

    And the shipment identified by io_S30916_130 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # ─── First send: complete → Enqueued → /ok → Sent → SSCC recorded in the ledger ────
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_130 | scriptedCfg_S30916_130                            | U            |

    When the scripted-export /ok callback is posted for shipment io_S30916_130 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_130 | scriptedCfg_S30916_130                            | S            |

    And after not more than 10s, EDI_EPCIS_Transmitted_SSCC is found:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031300 | scriptedCfg_S30916_130                            | io_S30916_130 |
    Then the EPCIS transmission ledger contains exactly:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031300 | scriptedCfg_S30916_130                            | io_S30916_130 |

    # The ledger now excludes this SSCC → the outbound-selection gate flips to false.
    And the EPCIS export-relevance for M_InOut identified by io_S30916_130 is false

    # ─── Duplicate trigger: support invokes the real production "Re-send" action ────────
    # get_epcis_events_json_fn now yields no pallets for this shipment, so nothing is re-transmitted.
    When M_InOut_ReSend_ScriptedExportConversion process is run for shipment io_S30916_130

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # No duplicate: the ledger still holds EXACTLY ONE row for the physical SSCC.
    Then the EPCIS transmission ledger contains exactly:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031300 | scriptedCfg_S30916_130                            | io_S30916_130 |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @ghActions:run_on_executor7
  @Id:S30916_140
  Scenario: S30916_140 — an IN-FLIGHT (Enqueued, unconfirmed) EPCIS export blocks reverse; deactivating the status row releases it
  ## Closes the async race the ledger alone leaves open (Q2, second path). The transmission ledger
  ## row is written only on the /ok success callback, so between dispatch-to-receiver and that
  ## callback the export is in-flight (status Enqueued/SendingStarted) with NO ledger row yet — but
  ## the SSCC may already be at the receiver. Reversing/reactivating in that window and re-completing
  ## would re-transmit the same physical SSCC. The guard must therefore reject reverse/reactivate/void
  ## while an EPCIS export is in-flight, not only after it is confirmed-and-ledgered.
  ##
  ## This scenario drives a standalone shipment to Enqueued (it does NOT post the /ok callback, so no
  ## ledger row exists), then: (1) reverse is rejected purely on the in-flight status; (2) after the
  ## stuck in-flight status row is deactivated (the WebUI escape-hatch, for the degenerate case where
  ## the external system never calls back), reverse succeeds.

    And the EPCIS transmission ledger is empty

    And metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_140 | 4060000001400 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_140 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_140 | ps_S30916_140      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_140 | pl_S30916_140  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_140         | p_S30916_140 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_140 | Y          | ps_S30916_140      | 9900000314000 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_140 | true                 | 9900000314000         | edi_setting_S30916_140_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_140 | p_S30916_140 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S30916_140  |
      | pi_TU_S30916_140  |
      | pi_VHU_S30916_140 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S30916_140  | pi_LU_S30916_140  | LU          | Y         |
      | piv_TU_S30916_140  | pi_TU_S30916_140  | TU          | Y         |
      | piv_VHU_S30916_140 | pi_VHU_S30916_140 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_140 | piv_LU_S30916_140  | 20  | HU       | pi_TU_S30916_140  |
      | pii_TU_S30916_140 | piv_TU_S30916_140  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_140  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_140          | pii_TU_S30916_140 | p_S30916_140 | 10  | 2020-01-01 |

    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_S30916_140      | scriptedCfg_S30916_140                            | M_InOut_EPCIS_Export_JSON        | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_140 | true    | bp_S30916_140 | 2026-06-10  | 1300000014  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_140 | o_S30916_140 | p_S30916_140 | 10         | pip_S30916_140          |

    When the order identified by o_S30916_140 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_140 | ol_S30916_140  | N             |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_140            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_140            | invLine_S30916_140            | p_S30916_140            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_140'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_140            | cu_S30916_140      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_140       | 10    | pip_S30916_140                     | tu_S30916_140                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_140       | 1     | pii_LU_S30916_140          | lu_S30916_140             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30916_140 | SSCC18               | 987654321000031400 |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_140      | ss_S30916_140                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_140      | ss_S30916_140                    |

    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_140         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30916_140         | io_S30916_140 |

    And the shipment identified by io_S30916_140 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # ─── In-flight: the export is Enqueued but NOT confirmed. No /ok is posted, so there is NO ledger
    # row — the shipment is blockable only via the in-flight status, which is exactly what this proves.
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_140 | scriptedCfg_S30916_140                            | U            |

    # Guard rejects reverse while the export is in-flight (SSCC may already be at the receiver).
    And the shipment identified by io_S30916_140 is reversed expecting error
      | AD_Message_ID |
      |               |

    # ─── Escape-hatch: deactivating the stuck in-flight status row releases the shipment ──────────
    When the EPCIS scripted-export status row for shipment io_S30916_140 is deactivated

    And the shipment identified by io_S30916_140 is reversed

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @ghActions:run_on_executor7
  @Id:S30916_150
  Scenario: S30916_150 — re-send of an errored shipment whose SSCCs are all already in the ledger sends NOTHING (DontSend), not an empty event
  ## Re-send gate (nothing-new → no empty event). A shipment is completed and Enqueued, then its
  ## EPCIS send ERRORS (so the config becomes re-sendable). Its physical SSCC is then recorded in
  ## the transmission ledger (a prior confirmed send of the same physical pallet). The production
  ## "Re-send" action is invoked: because epcis_has_events(...) is now false, the re-send must NOT
  ## invoke the adapter (which would emit an empty EPCIS event) — it records DontSend instead, and
  ## the ledger stays at exactly one row.

    And the EPCIS transmission ledger is empty

    And metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_150 | 4060000001500 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_150 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_150 | ps_S30916_150      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_150 | pl_S30916_150  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_150         | p_S30916_150 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_150 | Y          | ps_S30916_150      | 9900000315000 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_150 | true                 | 9900000315000         | edi_setting_S30916_150_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_150 | p_S30916_150 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S30916_150  |
      | pi_TU_S30916_150  |
      | pi_VHU_S30916_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S30916_150  | pi_LU_S30916_150  | LU          | Y         |
      | piv_TU_S30916_150  | pi_TU_S30916_150  | TU          | Y         |
      | piv_VHU_S30916_150 | pi_VHU_S30916_150 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_150 | piv_LU_S30916_150  | 20  | HU       | pi_TU_S30916_150  |
      | pii_TU_S30916_150 | piv_TU_S30916_150  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_150  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_150          | pii_TU_S30916_150 | p_S30916_150 | 10  | 2020-01-01 |

    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_S30916_150      | scriptedCfg_S30916_150                            | M_InOut_EPCIS_Export_JSON        | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    # Sales order: 10 PCE = 1 TU, one standalone pallet
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_150 | true    | bp_S30916_150 | 2026-06-10  | 1300000015  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_150 | o_S30916_150 | p_S30916_150 | 10         | pip_S30916_150          |

    When the order identified by o_S30916_150 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_150 | ol_S30916_150  | N             |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_150            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_150            | invLine_S30916_150            | p_S30916_150            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_150'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_150            | cu_S30916_150      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_150       | 10    | pip_S30916_150                     | tu_S30916_150                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_150       | 1     | pii_LU_S30916_150          | lu_S30916_150             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30916_150 | SSCC18               | 987654321000031500 |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_150      | ss_S30916_150                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_150      | ss_S30916_150                    |

    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=false and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_150         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30916_150         | io_S30916_150 |

    And the shipment identified by io_S30916_150 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # ─── First send attempt reaches Enqueued (ledger still empty → export-relevant) ─────
    Then the EPCIS export-relevance for M_InOut identified by io_S30916_150 is true
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_150 | scriptedCfg_S30916_150                            | U            |

    # ─── The send ERRORS → the config becomes re-sendable (latest attempt Error) ────────
    And the external system sends an error response for the shipment
      | M_InOut_ID    | ErrorMessage        |
      | io_S30916_150 | EPCIS_error_nosend  |
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_150 | scriptedCfg_S30916_150                            | E            |

    # ─── The shipment's physical SSCC is already in the ledger (prior confirmed send of the shared
    # pallet) → the outbound-selection gate epcis_has_events(...) flips to false. ──────────
    And metasfresh contains EDI_EPCIS_Transmitted_SSCC:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031500 | scriptedCfg_S30916_150                            | io_S30916_150 |
    And the EPCIS export-relevance for M_InOut identified by io_S30916_150 is false

    # ─── Re-send with nothing new: the gate records DontSend and does NOT invoke the adapter ──
    When M_InOut_ReSend_ScriptedExportConversion process is run for shipment io_S30916_150

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # The latest status is DontSend (N) — no Enqueued/Sent re-send attempt was produced, so no empty
    # EPCIS event was transmitted; the ledger still holds EXACTLY ONE row (no duplicate).
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID    | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_S30916_150 | scriptedCfg_S30916_150                            | N            |
    Then the EPCIS transmission ledger contains exactly:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000031500 | scriptedCfg_S30916_150                            | io_S30916_150 |

