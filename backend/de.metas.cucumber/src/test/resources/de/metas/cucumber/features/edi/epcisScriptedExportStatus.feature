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
