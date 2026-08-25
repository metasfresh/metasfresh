@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor3
Feature: EDI DESADV export via External System

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-15T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |

    Given metasfresh contains M_Products:
      | Identifier | GTIN        |
      | product    | productGTIN |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |

    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | externalSystemConfig_1   | scriptedExportConversion_1                        | M_InOut_EDI_Export_JSON          | M_InOut   |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Value               | Name               | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | desadvReceiverValue | desadvReceiverName | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | Identifier                     |
      | customer1     | true                 | 1234567890            | E                    | externalSystemConfig_1             | edi_setting_desadv_extSys_cust1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault | GLN           |
      | bpartner_location_1 | customer1     | Y               | Y               | 1234567890123 |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | SeqNo | GTIN |
      | asi_product_customer1 | product | customer1 | 10 | 0575095404663 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  Scenario: create a shipment and export DESADV via external system
    And RabbitMQ MF_TO_ExternalSystem queue is purged
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference   |
      | o_1        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | testReference |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_1       | o_1                   | product      | 100        |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_1                      | customer1                | o_1                   | P                |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | d_1                      |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_1                   | S                |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    And the external system sends an error response for the shipment
      | M_InOut_ID | ErrorMessage                              |
      | s_1        | External system export failed: Test error |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_1                   | E                |

    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | d_1                      | E                |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | d_1                      |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_1                   | S                |

    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | d_1                      | S                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  Scenario: create a shipment and enqueue single shipment to export DESADV via external system
    And RabbitMQ MF_TO_ExternalSystem queue is purged
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference    |
      | o_2        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | testReference2 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_2_1     | o_2                   | product      | 10         |

    And the order identified by o_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2_1                    | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_2                      | customer1                | o_2                   | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_2        |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_2                   | S                |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    And the external system sends an error response for the shipment
      | M_InOut_ID | ErrorMessage                              |
      | s_2        | External system export failed: Test error |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | s_2        | E                |

    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus |
      | d_2           | E                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_2        |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | s_2        | S                |

    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus |
      | d_2           | S                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  @Id:S29231_140
  Scenario: S29231_140 — Two orders, one consolidated shipment → array-mode fan-out exports both DESADVs via External System path
  ## Two EDI-DESADV-recipient orders for the same BPartner (each with a distinct POReference)
  ## are completed (creating one EDI_Desadv per order). Their shipment schedules are
  ## consolidated into a single M_InOut (C_Order_ID = null — lines from 2 different orders).
  ## End-state assertion: both source DESADVs (dA_140 + dB_140) and the consolidated
  ## shipment (io_140) must reach EDI_ExportStatus=S within 120 s.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # Order A — distinct POReference → its own EDI_Desadv at order-complete.
    # @Date@ suffix keeps POReferences unique across local repeat runs (DB pollution guard).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference              |
      | oA_140     | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_A_S29231_140_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | olA_140    | oA_140                | product      | 10         |

    And the order identified by oA_140 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dA_140                   | customer1                | oA_140                | P                |

    # Order B — different POReference → its own distinct EDI_Desadv
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference              |
      | oB_140     | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_B_S29231_140_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | olB_140    | oB_140                | product      | 10         |

    And the order identified by oB_140 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dB_140                   | customer1                | oB_140                | P                |

    # Both shipment schedules must be ready before batching into one M_InOut
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssA_140    | olA_140                   | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssB_140    | olB_140                   | N             |

    # Batch-generate ONE shipment covering both schedules (the aggregated M_InOut).
    # The legacy interceptor sets M_InOut.C_Order_ID = null because the lines come from 2 orders.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ssA_140               |
      | ssB_140               |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ssA_140               | io_140     |

    # Enqueue one M_InOut export — production fans out to N HTTP calls; the cucumber mock writes status directly via RabbitMQ.
    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | io_140     |

    # ─── CORE ASSERTION ────────────────────
    # Both source DESADVs must reach Sent (S) within 120 s — the status recompute
    # must traverse the junction to find the consolidated shipment for both DESADVs.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | dA_140                   | S                |
      | dB_140                   | S                |

    # The consolidated shipment also reaches Sent once both DESADVs are processed.
    And after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | io_140                | S                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  @Id:S30189_150
  Scenario: S30189_150 — Same C_BPartner, two ship-to locations → DESADV routing decided by C_BPartner_Location_ID
  ## Proves that EDI DESADV routing is driven by C_BPartner_Location_ID, not by the partner alone.
  ## One partner (customer1) has two location-specific C_BPartner_EDI_Setting rows:
  ##   (customer1, loc_ext_150) → EdiDESADVSendingMode=E (ExternalSystem)
  ##   (customer1, loc_repl_150) → EdiDESADVSendingMode=R (ReplicationInterface)
  ## Two shipments are created — one per location.  After processing:
  ##   loc_ext_150 shipment → exported via external system → M_InOut.EDI_ExportStatus=S
  ##   loc_repl_150 DESADV  → NOT exported via external system → EDI_ExportStatus stays P
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # ── Two distinct ship-to locations for the same partner ──────────────────
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | GLN           |
      | loc_ext_150  | customer1     | Y        | Y        | N               | N               | 0300000150111 |
      | loc_repl_150 | customer1     | Y        | Y        | N               | N               | 0300000150222 |

    # ── Location-specific EDI settings ───────────────────────────────────────
    # SeqNo=5 on both location-specific rows so they win over the null-location Background row (SeqNo=10).
    # loc_ext_150: ExternalSystem path (same externalSystemConfig_1 as Background)
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | C_BPartner_Location_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | SeqNo | Identifier              |
      | customer1     | loc_ext_150            | true                 | 1234567891            | E                    | externalSystemConfig_1             | 5     | edi_setting_loc_ext_150 |
    # loc_repl_150: ReplicationInterface path (no ExternalSystem config needed)
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | C_BPartner_Location_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | SeqNo | Identifier               |
      | customer1     | loc_repl_150           | true                 | 1234567892            | R                    | 5     | edi_setting_loc_repl_150 |

    # ── Order A → loc_ext_150 (ExternalSystem path) ──────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DeliveryRule | DateOrdered | DatePromised | POReference              |
      | o_ext_150  | true    | customer1     | loc_ext_150            | F            | 2025-04-17  | 2025-04-18Z  | PO_ext_S30189_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_ext_150  | o_ext_150             | product      | 10         |

    And the order identified by o_ext_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_ext_150                | customer1                | o_ext_150             | P                |

    # ── Order B → loc_repl_150 (ReplicationInterface path) ───────────────────
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DeliveryRule | DateOrdered | DatePromised | POReference               |
      | o_repl_150  | true    | customer1     | loc_repl_150           | F            | 2025-04-17  | 2025-04-18Z  | PO_repl_S30189_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_repl_150  | o_repl_150            | product      | 10         |

    And the order identified by o_repl_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_repl_150               | customer1                | o_repl_150            | P                |

    # ── Generate one shipment per shipment schedule ───────────────────────────
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_ext_150  | ol_ext_150                | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_repl_150  | ol_repl_150               | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_ext_150                        | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ext_150                        | io_ext_150            |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_repl_150                       | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_repl_150                       | io_repl_150           |

    # ── Enqueue loc_ext_150 shipment → must go to ExternalSystem ─────────────
    And M_InOut is enqueued for EDI export
      | M_InOut_ID  |
      | io_ext_150  |

    # ─── CORE ASSERTION: loc_ext_150 → ExternalSystem path ───────────────────
    # The M_InOut for loc_ext_150 must reach Sent (S) — routed via external system.
    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | io_ext_150            | S                |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    # ─── CORE ASSERTION: loc_repl_150 → NOT ExternalSystem path ──────────────
    # The DESADV for loc_repl_150 was never enqueued to the external system.
    # Its EDI_ExportStatus must remain P (pending) — proving a different route was taken.
    And after not more than 5s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | d_repl_150               | P                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_10
  Scenario: S30013_10 — under-delivery, remaining M_ShipmentSchedule closed -> DESADV reaches Sent
  ## An order for 100 PCE is only delivered 70 PCE; that one shipment is exported successfully.
  ## Intermediate state: the DESADV is under-delivered (FulfillmentPercent=70) and therefore
  ## still Pending — nothing may close it while more M_InOutLines could still arrive.
  ## Closing the remaining M_ShipmentSchedule is the statement "no more will ever be delivered",
  ## so the DESADV must reach its terminal status S / Processed on its own, without running the
  ## EDI_Desadv_Close process.  FulfillmentPercent must stay 70 — it is an EXP_FormatLine, i.e.
  ## transmitted content, and auto-closing may not change what the recipient receives.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs: the DESADV is resolved
    # by POReference + C_BPartner_ID, so a fixed value would re-use a previous run's DESADV.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference           |
      | o_10       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_10_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_10_1    | o_10                  | product      | 100        |

    And the order identified by o_10 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_10     | ol_10_1                   | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open.
    # QtyToDeliver_Override_For_M_ShipmentSchedule_ID is the workpackage-parameter name that
    # ShipmentScheduleWorkPackageParameters.PARAM_QtyToDeliver_Override resolves to; the short
    # alias "QtyToDeliver_Override" does NOT match and the full 100 would be shipped.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_10                           | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_10                           | s_10                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_10                     | customer1                | o_10                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_10       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_10                  | S                |

    # Intermediate state: everything that was shipped is exported, but the DESADV is under-delivered
    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.Processed | OPT.FulfillmentPercent |
      | d_10          | P                | false         | 70                     |

    When the M_ShipmentSchedule identified by s_s_10 is closed

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # OPT.EDIErrorMsg=null: the auto-close is a clean terminal state, not an error state.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_10          | S                | null            | true          | 70                     |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_20
  Scenario: S30013_20 — under-delivery, M_ShipmentSchedule left open -> DESADV stays Pending
  ## The unchanged case, and the signal disposition relies on: as long as the remaining
  ## M_ShipmentSchedule is open, more M_InOutLines can still arrive, so the under-delivered
  ## DESADV must stay P / not Processed even though every shipment it has is exported.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference           |
      | o_20       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_20_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_20_1    | o_20                  | product      | 100        |

    And the order identified by o_20 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_20     | ol_20_1                   | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open (see S30013_10 on the column name).
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_20                           | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_20                           | s_20                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_20                     | customer1                | o_20                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_20       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_20                  | S                |

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # The schedule is deliberately NOT closed — this is the signal disposition relies on.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.Processed | OPT.FulfillmentPercent |
      | d_20          | P                | false         | 70                     |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_30
  Scenario: S30013_30 — M_ShipmentSchedule closed after the DESADV was already exported -> DESADV reaches Sent
  ## The production ordering: the export finished first, the close came later.
  ## An order for 2 x 100 PCE is delivered and exported for the first line only, so the DESADV is
  ## under-delivered (FulfillmentPercent=50) and stays Pending even though its only shipment is
  ## already Sent.  Closing the second line's M_ShipmentSchedule afterwards is the statement "the
  ## other 100 will never be delivered", so the already-exported DESADV must reach S on its own.
  ## FulfillmentPercent must stay 50 — it is an EXP_FormatLine, i.e. transmitted content.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference         |
      | o_30       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_30_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_30_1    | o_30                  | product      | 100        |
      | ol_30_2    | o_30                  | product      | 100        |

    And the order identified by o_30 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_30_1   | ol_30_1                   | N             |
      | s_s_30_2   | ol_30_2                   | N             |

    # Only the first line is shipped; the second line's schedule stays open with nothing delivered.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_30_1                         | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_30_1                         | s_30                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_30                     | customer1                | o_30                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_30       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_30                  | S                |

    # The export is finished — and the DESADV is still Pending because line ol_30_2 is undelivered.
    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_30          | P                | null            | false         | 50                     |

    When the M_ShipmentSchedule identified by s_s_30_2 is closed

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # The close arrives after the export already completed, and still closes the DESADV.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_30          | S                | null            | true          | 50                     |

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_40
  Scenario: S30013_40 — nothing was sent and everything is closed -> DESADV reaches DontSend
  ## The branch that must yield DontSend rather than Sent, matching what the EDI_Desadv_Close process
  ## writes.  An order for 100 PCE is delivered 70 PCE, but that shipment is not transmitted at all —
  ## it carries DontSend, the status a shipment gets when its ship-to location is no EDI DESADV
  ## recipient.  Closing the remaining M_ShipmentSchedule then says "the other 30 will never be
  ## delivered", so the DESADV reaches its terminal status too — and since not one of its shipments
  ## was ever sent, that terminal status is DontSend, not Sent.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference         |
      | o_40       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_40_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_40_1    | o_40                  | product      | 100        |

    And the order identified by o_40 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_40     | ol_40_1                   | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open (see S30013_10 on the column name).
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_40                           | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_40                           | s_40                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_40                     | customer1                | o_40                  | P                |

    # The shipment is never enqueued for export; it is marked as one that shall not be sent at all.
    And the EDI export status of the following M_InOut records is set:
      | M_InOut_ID | EDI_ExportStatus |
      | s_40       | N                |

    When the M_ShipmentSchedule identified by s_s_40 is closed

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # No shipment of this DESADV was ever sent, so the terminal status is DontSend (N), not Sent.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_40          | N                | null            | true          | 70                     |

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_50
  Scenario: S30013_50 — the automatic close of the DESADV transmits nothing
  ## Closing itself sends nothing.  An order for 100 PCE is delivered 70 PCE and that shipment is
  ## exported, which puts one JsonExternalSystemRequest on the queue — the positive control that
  ## proves this scenario can see a transmission.  That message is consumed, the queue is emptied,
  ## and only then is the remaining M_ShipmentSchedule closed.  The DESADV reaches its terminal
  ## status S, and nothing new leaves the system: no further message on the queue, and the shipment
  ## still sits at S rather than having been walked through Enqueued / SendingStarted again.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference         |
      | o_50       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_50_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_50_1    | o_50                  | product      | 100        |

    And the order identified by o_50 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_50     | ol_50_1                   | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open (see S30013_10 on the column name).
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_50                           | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_50                           | s_50                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_50                     | customer1                | o_50                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_50       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_50                  | S                |

    # Positive control: the export DID put a message on the queue, and this step consumes it.
    # Whatever the close does afterwards is therefore measured against a queue known to work.
    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.Processed | OPT.FulfillmentPercent |
      | d_50          | P                | false         | 70                     |

    When the M_ShipmentSchedule identified by s_s_50 is closed

    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_50          | S                | null            | true          | 70                     |

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # Nothing was transmitted by the close: no message on the queue, and the shipment was not
    # re-exported (a re-export would have moved it through Enqueued / SendingStarted).
    Then RabbitMQ MF_TO_ExternalSystem receives no message within 30s
    And after not more than 5s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_50                  | S                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_60
  Scenario: S30013_60 — closing all M_ShipmentSchedules of a three-line order -> DESADV reaches Sent
  ## The close fires the DESADV recompute once per closed M_ShipmentSchedule, so a three-line order
  ## runs it three times.  An order for 3 x 100 PCE is delivered 70 PCE per line in one shipment,
  ## which is exported; the DESADV is under-delivered at 70% and stays Pending.  Closing all three
  ## remaining M_ShipmentSchedules must leave it at exactly one terminal state, S / Processed, with
  ## the transmitted FulfillmentPercent untouched.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference         |
      | o_60       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_60_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_60_1    | o_60                  | product      | 100        |
      | ol_60_2    | o_60                  | product      | 100        |
      | ol_60_3    | o_60                  | product      | 100        |

    And the order identified by o_60 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_60_1   | ol_60_1                   | N             |
      | s_s_60_2   | ol_60_2                   | N             |
      | s_s_60_3   | ol_60_3                   | N             |

    # All three schedules go into ONE workpackage, so one shipment covers the whole order;
    # each line delivers 70 of its 100 (see S30013_10 on the override column name).
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_60_1              | 70                                              |
      | s_s_60_2              | 70                                              |
      | s_s_60_3              | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_60_1                         | s_60                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_60                     | customer1                | o_60                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_60       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_60                  | S                |

    # 210 of 300 ordered delivered => 70%: every one of the three lines is short.
    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.Processed | OPT.FulfillmentPercent |
      | d_60          | P                | false         | 70                     |

    # TC8 requires ONE operation: the real M_ShipmentSchedule_CloseShipmentSchedules AD_Process is run once
    # over a three-row selection, so the interceptor fires once per line inside a single close operation.
    When the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:
      | M_ShipmentSchedule_ID |
      | s_s_60_1              |
      | s_s_60_2              |
      | s_s_60_3              |

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # The DESADV is closed only once the LAST line can no longer receive anything.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_60          | S                | null            | true          | 70                     |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_70
  Scenario: S30013_70 — reopening the M_ShipmentSchedule returns the DESADV to Pending without re-sending
  ## The close is reversible.  An order for 100 PCE is delivered 70 PCE, exported, and auto-closed to
  ## S by closing the remaining M_ShipmentSchedule.  Reopening that schedule says "more may still be
  ## delivered after all", so the DESADV must return to P / not Processed and accept further
  ## M_InOutLines again — and reopening must not re-transmit anything: no message on the queue, and
  ## the shipment stays at S instead of being walked through Enqueued / SendingStarted a second time.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference         |
      | o_70       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | PO_S30013_70_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_70_1    | o_70                  | product      | 100        |

    And the order identified by o_70 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_70     | ol_70_1                   | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open (see S30013_10 on the column name).
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_70                           | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_70                           | s_70                  |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_70                     | customer1                | o_70                  | P                |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_70       |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_70                  | S                |

    # Positive control: the export DID put a message on the queue, and this step consumes it, so the
    # "no message" assertion after the reopen is measured against a queue known to deliver.
    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    When the M_ShipmentSchedule identified by s_s_70 is closed

    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_70          | S                | null            | true          | 70                     |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When the M_ShipmentSchedule identified by s_s_70 is reactivated

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # Back to Pending because the reopened schedule can deliver again — and nothing was re-sent:
    # no message on the queue, and the shipment was not re-exported.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_70          | P                | null            | false         | 70                     |

    Then RabbitMQ MF_TO_ExternalSystem receives no message within 30s
    And after not more than 5s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_70                  | S                |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @Id:S30013_100
  Scenario: S30013_100 — location-level 'E' routing outranks the partner's 'R' default -> the under-delivery still auto-closes
  ## The routing shape a production instance actually runs.  Every other scenario in this file inherits
  ## the Background's single partner-level C_BPartner_EDI_Setting with EdiDESADVSendingMode=E and no
  ## location, so per-M_InOut export mode is reached trivially.  In production the
  ## de.metas.edi.OneDesadvPerShipment sysconfig route is off and the mode comes from a
  ## location-bound 'E' row that outranks the partner's own 'R' default on SeqNo — see
  ## EDIBPartnerConfigRepository.resolve(): among the rows whose C_BPartner_Location_ID is null or
  ## matches exactly, the minimum SeqNo wins, then the minimum ID.
  ## This scenario builds that shape (partner-level 'R' at SeqNo 20, location-bound 'E' at SeqNo 10)
  ## and then runs S30013_10's flow on top of it: deliver 70 of the 100 ordered, export that shipment,
  ## close the remaining M_ShipmentSchedule, and the DESADV must auto-close to S on its own.
  ## S30189_150 covers location-driven routing in isolation; this is the only scenario that composes it
  ## with an under-delivery close, i.e. the combination the fix has to survive at the customer.
    And RabbitMQ MF_TO_ExternalSystem queue is purged

    # A partner of its own is required: the Background already owns the (customer1, no location) row,
    # and this step upserts on (C_BPartner_ID, C_BPartner_Location_ID), so giving customer1 a
    # partner-level 'R' default would overwrite that row and break every sibling scenario.
    And metasfresh contains C_BPartners without locations:
      | Identifier | Value                | Name                | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer2  | desadvReceiver2Value | desadvReceiver2Name | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | GLN           |
      | bpartner_location_2 | customer2     | Y        | Y        | Y               | Y               | 2234567890123 |

    # The tie-break under test: for bpartner_location_2 the location-bound 'E' row (SeqNo 10) beats the
    # partner's own ReplicationInterface default (SeqNo 20), so this order runs in per-M_InOut mode and
    # is exported through externalSystemConfig_1.  The blank C_BPartner_Location_ID cell on the first
    # row is what makes it the partner-level default (the column is optional in this step).
    And metasfresh contains C_BPartner_EDI_Setting:
      | Identifier                      | C_BPartner_ID | C_BPartner_Location_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | SeqNo |
      | edi_setting_desadv_repl_cust2   | customer2     |                        | true                 | 2234567890            | R                    |                                    | 20    |
      | edi_setting_desadv_extSys_cust2 | customer2     | bpartner_location_2    | true                 | 2234567891            | E                    | externalSystemConfig_1             | 10    |

    # @Date@ suffix keeps the POReference unique across local repeat runs (see S30013_10).
    # DeliveryRule=F is mandatory here and NOT an arbitrary extra: MOrder.beforeSave copies the
    # partner's DeliveryRule onto the order only while C_BPartner_Location_ID is still unset, and this
    # order must name its location explicitly to hit the location-bound EDI setting.  Without it the
    # order keeps the column default 'A' (Availability), there is no stock in the test DB, and
    # 'generate shipments' aborts with "nothing left to deliver".  S30189_150 — the other scenario that
    # names the location — passes F for the same reason.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DeliveryRule | DateOrdered | DatePromised | POReference          |
      | o_100      | true    | customer2     | bpartner_location_2    | F            | 2025-04-17  | 2025-04-18Z  | PO_S30013_100_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_100_1   | o_100                 | product      | 100        |

    And the order identified by o_100 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_100    | ol_100_1                  | N             |

    # Deliver only 70 of the 100 ordered, so a remainder stays open (see S30013_10 on the column name).
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_100                          | D            | true                | false       | 70                                              |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_100                          | s_100                 |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | d_100                    | customer2                | o_100                 |

    And M_InOut is enqueued for EDI export
      | M_InOut_ID |
      | s_100      |

    Then after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | s_100                 | S                |

    # ─── ROUTING ASSERTION ─────────────────────────────────────────────────────
    # Only the external-system route puts a message on MF_TO_ExternalSystem.  Had the partner-level 'R'
    # row won the SeqNo tie-break, the export would have taken the ReplicationInterface route and this
    # step would time out — which is exactly the production precondition being asserted here.
    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    # Intermediate state: everything that was shipped is exported, but the DESADV is under-delivered
    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.Processed | OPT.FulfillmentPercent |
      | d_100         | P                | false         | 70                     |

    When the M_ShipmentSchedule identified by s_s_100 is closed

    # ─── CORE ASSERTION ────────────────────────────────────────────────────────
    # Same terminal state as S30013_10, reached under the production routing shape.
    # OPT.EDIErrorMsg=null: the auto-close is a clean terminal state, not an error state.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID | EDI_ExportStatus | OPT.EDIErrorMsg | OPT.Processed | OPT.FulfillmentPercent |
      | d_100         | S                | null            | true          | 70                     |
