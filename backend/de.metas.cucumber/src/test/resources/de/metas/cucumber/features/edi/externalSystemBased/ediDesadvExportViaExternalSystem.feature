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
    And the following c_bpartner is changed
      | Identifier | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID |
      | customer1  | true                 | 1234567890            | E                    | externalSystemConfig_1             |
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
  Scenario: S29231_140 — Two orders, one consolidated shipment → both DESADVs export via External System path
  ## me03#29231 — End-to-end coverage of Tasks 1+4+6+7+9+9b through the External-System path.
  ## Two EDI-DESADV-recipient orders for the same BPartner (each with a distinct POReference)
  ## are completed (creating one EDI_Desadv per order). Their shipment schedules are
  ## consolidated by 'generate shipments' batch into a single M_InOut whose C_Order_ID is null
  ## (the legacy interceptor null-trick because lines come from 2 different orders).
  ## After enqueuing BOTH DESADVs the canonical PostgREST + scripted-adapter path must run
  ## per DESADV and both DESADVs + the consolidated shipment must reach EDI_ExportStatus=S.
  ## Before T1+T9b: the shared shipment was only linked to one (winner) DESADV, so the
  ## non-winner DESADV could never reach S. The junction table (T1) and per-DESADV status
  ## propagation (T9 + T9b) together make this scenario succeed.
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

    # Batch-generate ONE shipment covering both schedules (the aggregated M_InOut)
    # The legacy interceptor sets M_InOut.C_Order_ID = null because the lines come from 2 orders.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ssA_140               |
      | ssB_140               |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ssA_140               | io_140     |

    # Enqueue both DESADVs — each fires its own scripted-adapter PostgREST call,
    # filtered by EDI_Desadv_ID per T6.
    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | dA_140                   |
      | dB_140                   |

    # ─── CORE ASSERTION (TC4 falsifiable predicate) ───────────────────────────
    # Both source DESADVs must reach Sent (S) within 120s — only possible because
    # T1+T9+T9b together let recomputeDesadvStatusFromInOuts traverse the junction
    # to find the consolidated shipment for BOTH source DESADVs.
    Then after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | dA_140                   | S                |
      | dB_140                   | S                |

    # The consolidated shipment also reaches Sent once both DESADVs are processed.
    And after not more than 120s, M_InOut records have the following export status
      | M_InOut_ID.Identifier | EDI_ExportStatus |
      | io_140                | S                |
