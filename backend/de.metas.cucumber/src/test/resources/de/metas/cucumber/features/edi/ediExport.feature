@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
Feature: EDI_cctop_invoic_v export format
## F00350: EDI
## F00350: EDI

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS

  #   Convenience Salat 250g
    And load M_Product:
      | Identifier        | OPT.M_Product_ID |
      | convenienceSalate | 2005577          |
      | ifco6410          | 2001343          |
    And update M_Product:
      | Identifier | IsActive |
      | ifco6410   | Y        |

  Scenario: As a user I want to export C_Invoice using EDI_cctop_invoic_v export format

  #   IFCO 6410 x 10 Stk
    Given update M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.GTIN        |
      | 3010001                            | itemProductGTIN |

    And update M_Product:
      | M_Product_ID.Identifier | OPT.GTIN    |
      | convenienceSalate       | productGTIN |

  # Test Kunde 1
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.Name2 | OPT.VATaxID     | OPT.DeliveryRule |
      | 2156425                  | name2     | bPartnerVaTaxID | F                |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | IsEdiInvoicRecipient | EdiInvoicRecipientGLN      | Identifier             |
      | 2156425       | true                 | bPartnerDesadvRecipientGLN | true                 | bPartnerInvoicRecipientGLN | edi_setting_export_sc1 |

  # metasfresh AG
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.VATaxID     |
      | 2155894                  | bPartnerVaTaxID |

    And metasfresh contains M_Product_ASI_Data:
      | Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | SeqNo | GTIN          | EAN_CU        |
      | asi_1      | convenienceSalate       | 2156425                  | 10    | 0575095404663 | 0575095404663 |

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.GLN       |
      | 2205175                           | 1234567890123 |

    And update C_Location of the following C_BPartner_Location
      | C_BPartner_Location_ID.Identifier | OPT.Address1 | OPT.Address2 | OPT.Address3 |
      | 2205175                           | address1     | address2     | address3     |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | type_1                           | RabbitMQ |

    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name               | EXP_Processor_Type_ID.Identifier |
      | ep_1                        | ediExportProcessor | type_1                           |

    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name     | EntityType | EXP_Processor_ID.Identifier |
      | rs_1                                 | rabbitMQ | U          | ep_1                        |

  #  metasfresh
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | rs_1                                 |

    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue |
      | ep_1                        | exchangeName   | ediExport      |
      | ep_1                        | routingKey     | ediExport      |
      | ep_1                        | isDurableQueue | true           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | 2156425                  | 2021-04-17  | po_ref_23062023 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | convenienceSalate       | 10         | 3010001                                |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |

    # if we enqueue all ICs for the order, we get two invoices, because there is the IC of the packaging material, which has IsEdiEnabled=N
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference     | paymentTerm | processed | DocStatus |
      | invoice_1               | 2156425                  | 2205175                           | po_ref_23062023 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il1                         | invoice_1               | convenienceSalate       | 10          | true      |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | d_1                      | 2156425                  | o_1                   |

    And invoice is EDI exported
      | C_Invoice_ID.Identifier |
      | invoice_1               |

    And RabbitMQ receives a EDI_cctop_invoic_v
      | EDI_cctop_invoic_v_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | ic_1                             | ep_1                        | routingKey                   |

    And validate EDI_cctop_invoic_v:
      | EDI_cctop_invoic_v_ID.Identifier | OPT.EDIDesadvDocumentNo.Identifier |
      | ic_1                             | d_1                                |

    And EDI_cctop_invoic_500_v of the following EDI_cctop_invoic_v is validated
      | EDI_cctop_invoic_v_ID.Identifier | OPT.Buyer_GTIN_CU | OPT.Buyer_EAN_CU | OPT.Supplier_GTIN_CU | OPT.Buyer_GTIN_TU | OPT.GTIN        |
      | ic_1                             | 0575095404663     | 0575095404663    | productGTIN          | itemProductGTIN   | itemProductGTIN |

    And validate EDI_cctop_119_v within EDI_cctop_invoic_v ic_1 by location type
      | eancom_locationtype | OPT.Contact |
      | SU                  | metasfresh  |
      | DP                  | null        |
      | IV                  | null        |
      | BY                  | null        |
      | SN                  | null        |

    #  Test Kunde 1
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.Name2 | OPT.VATaxID | OPT.DeliveryRule |
      | 2156425                  | null      | null        | null             |

    #  metasfresh AG
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.VATaxID |
      | 2155894                  | null        |

    And update M_Product:
      | M_Product_ID.Identifier | OPT.GTIN |
      | convenienceSalate       | null     |

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.GLN |
      | 2205175                           | null    |

    And update M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.GTIN |
      | 3010001                            | null     |

  Scenario: As a user I want to export EDI_Exp_Desadv

    # Test Kunde 1
    Given the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.DeliveryRule |
      | 2156425                  | F                |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier             |
      | 2156425       | true                 | 1234567890123         | edi_setting_export_sc2 |

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.GLN       |
      | 2205175                           | 1234567890123 |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | type_1                           | RabbitMQ |

    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name      | EXP_Processor_Type_ID.Identifier |
      | ep_1                        | ediExport | type_1                           |

    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name     | EntityType | EXP_Processor_ID.Identifier |
      | rs_1                                 | rabbitMQ | U          | ep_1                        |

    #  metasfresh
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | rs_1                                 |

    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue |
      | ep_1                        | exchangeName   | ediExport      |
      | ep_1                        | routingKey     | ediExport      |
      | ep_1                        | isDurableQueue | true           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | 2156425                  | 2021-04-17  | testReference   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | convenienceSalate       | 100        | 3010001                                |

    When the order identified by o_1 is completed

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
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | d_1                      | 2156425                  | o_1                   |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | d_1                      |

    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | d_1                      | U                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | e_d_1                        | ep_1                        | routingKey                   |

    And EDI_Exp_Desadv_Pack of the following EDI_Exp_Desadv is validated
      | EDI_Exp_Desadv_ID.Identifier | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerTU | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerLU | OPT.EDI_Exp_Desadv_Pack_Item.QtyTU |
      | e_d_1                        | 10                                       | 100                                      | 10                                 |

    # me03 #30308 — the replication-interface DESADV XML must carry the recipient GLN (resolved from
    # the partner-default C_BPartner_EDI_Setting row, edi_setting_export_sc2) AND its other
    # business-critical recipient/header elements. Superset check: catches a future silently-dropped
    # export element (e.g. an EXP_FormatLine cascade-deleted by a column drop — the #30238 failure mode).
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | OPT.UnderTag  | TagName                | OPT.Value     |
      | e_d_1                        | C_BPartner_ID | EdiRecipientGLN        | 1234567890123 |
      | e_d_1                        |               | C_BPartner_ID          |               |
      | e_d_1                        |               | C_BPartner_Location_ID |               |
      | e_d_1                        |               | ShipmentDocumentNo     |               |
      | e_d_1                        |               | MovementDate           |               |
      | e_d_1                        |               | EDI_Exp_Desadv_Pack    |               |

     #  Test Kunde 1
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.DeliveryRule |
      | 2156425                  | null             |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  @Id:S29231_150
  Scenario: S29231_150 — Two orders, one consolidated shipment → both DESADVs exported via the Replication Interface (RPL) path
  ## Regression for the RPL (One-DESADV-Per-Shipment EXP_Format) path: two orders with
  ## distinct POReferences → consolidated shipment → BOTH source DESADVs are exported.
  ## Uses EDI_Exp_Desadv (One-DESADV-Per-Shipment, ID=540405) which reads M_InOut_Desadv_V
  ## via the junction — verifying that both linked DESADVs are visible and exported.
    Given metasfresh is configured for One-DESADV-Per-Shipment

  # Fresh BPartner for S29231_150 — using a pre-existing seed BPartner with consolidation-blocking
  # data causes 'generate shipments' to emit two separate M_InOuts instead of one consolidated
  # shipment. A fresh BPartner guarantees a single consolidated M_InOut.
  # EdiDESADVSendingMode is left at the column default 'R' (ReplicationInterface).
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_150     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_150     | ps_150             | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_150    | pl_150         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_150                | convenienceSalate | 10.0     | PCE      | Normal           |
      | plv_150                | ifco6410          | 0.00     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_150     | Y          | ps_150             | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | bp_150        | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier           |
      | bp_150        | true                 | 1234567890123         | edi_setting_S150_bp1 |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | type_2                           | RabbitMQ |

    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name           | EXP_Processor_Type_ID.Identifier |
      | ep_2                        | ediExport_150  | type_2                           |

    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name        | EntityType | EXP_Processor_ID.Identifier |
      | rs_2                                 | rabbitMQ150 | U          | ep_2                        |

    #  metasfresh
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | rs_2                                 |

    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue   |
      | ep_2                        | exchangeName   | ediExport_150    |
      | ep_2                        | routingKey     | ediExport_150    |
      | ep_2                        | isDurableQueue | true             |

    # Order A uses the seed packing 3010001 (IFCO 6410 x 10 Stk): QtyCUsPerTU=10, QtyCUsPerLU=100.
    # Order B uses a dedicated packing (20 PCE/TU, LU=5 TUs): QtyCUsPerTU=20, QtyCUsPerLU=100.
    # Distinct QtyCUsPerTU values (10 vs 20) prove the RPL export preserves each source-order's
    # pack-item configuration rather than merging them into a single shared set.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID      |
      | pi_LU_B_150     |
      | pi_TU_B_150     |
      | pi_VHU_B_150    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID   | HU_UnitType | IsCurrent |
      | piv_LU_B_150       | pi_LU_B_150  | LU          | Y         |
      | piv_TU_B_150       | pi_TU_B_150  | TU          | Y         |
      | piv_VHU_B_150      | pi_VHU_B_150 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_B_150    | piv_LU_B_150       | 5   | HU       | pi_TU_B_150       |
      | pii_TU_B_150    | piv_TU_B_150       | 0   | PM       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID      | Qty | ValidFrom  |
      | pip_B_150               | pii_TU_B_150    | convenienceSalate | 20  | 2020-01-01 |

    # Order A — distinct POReference → its own EDI_Desadv at order-complete.
    # @Date@ suffix keeps POReferences unique across local repeat runs (DB pollution guard).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised | OPT.POReference        |
      | oA_150     | true    | bp_150                   | 2021-04-17  | 2021-04-18Z      | PO_A_S29231_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olA_150    | oA_150                | convenienceSalate       | 100        | 3010001                                |

    And the order identified by oA_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | dA_150                   | bp_150                   | oA_150                |

    # Order B — different POReference → its own distinct EDI_Desadv; uses pip_B_150 (20 PCE/TU)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised | OPT.POReference        |
      | oB_150     | true    | bp_150                   | 2021-04-17  | 2021-04-18Z      | PO_B_S29231_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olB_150    | oB_150                | convenienceSalate       | 100        | pip_B_150                              |

    And the order identified by oB_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | dB_150                   | bp_150                   | oB_150                |

    # Both shipment schedules must be ready before batching into one M_InOut
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssA_150    | olA_150                   | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssB_150    | olB_150                   | N             |

    # Batch-generate ONE shipment covering both schedules (the aggregated M_InOut).
    # The legacy interceptor sets M_InOut.C_Order_ID = null because the lines come from 2 orders.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ssA_150               |
      | ssB_150               |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ssA_150                          | io_150                |

    # The two source schedules MUST share the same M_InOut (the junction table links both DESADVs to it).
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ssB_150                          | io_150                |

    # Enqueue both DESADVs — each fires its own RPL export (EDI_DESADV_InOut_Export.doExport).
    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | dA_150                   |
      | dB_150                   |

    # Wait for the async workpackage processor to actually run sendAMQPMessage() and
    # reach status D (SendingStarted) — only then is the ediExport_150 queue declared
    # (Spring AMQP declares it on first publish). Status U (Enqueued) means the WP is
    # queued but not yet running, which is too early — pollDocumentFromQueue() racing
    # against queue declaration causes a 404 NOT_FOUND on slower CI runners.
    # Note: in the RPL/EDI_DESADVExport path the DESADV status transitions are:
    #   P → U (enqueued) → D (sendAMQPMessage called) → stays D after WP completes.
    # Status S (Sent) is only reached via the per-InOut path; the DESADV-level RPL path
    # never advances beyond D, so we wait for D as the "queue declared" sentinel.
    And after not more than 120s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | dA_150                   | D                |
      | dB_150                   | D                |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The RPL export emits one EDIFACT XML to RabbitMQ per processed DESADV.
    # We poll for both messages on the configured queue (ediExport_150 routing key).
    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | e_dA_150                     | ep_2                        | routingKey                   |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | e_dB_150                     | ep_2                        | routingKey                   |

    # ─── PACK-ITEM ASSERTIONS ─────────────────────────────────────────────────
    # Each DESADV must carry pack-item dimensions that match its source order's packing config.
    # DESADV A (oA_150): uses 3010001 (IFCO 6410 x 10 Stk) → QtyCUsPerTU=10, QtyCUsPerLU=100, QtyTU=10
    # DESADV B (oB_150): uses pip_B_150 (20 PCE/TU, LU=5 TUs)  → QtyCUsPerTU=20, QtyCUsPerLU=100, QtyTU=5
    # Distinct QtyCUsPerTU values (10 vs 20) prove per-source-DESADV projection is correct.
    And EDI_Exp_Desadv_Pack of the following EDI_Exp_Desadv is validated
      | EDI_Exp_Desadv_ID.Identifier | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerTU | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerLU | OPT.EDI_Exp_Desadv_Pack_Item.QtyTU |
      | e_dA_150                     | 10                                       | 100                                      | 10                                 |

    And EDI_Exp_Desadv_Pack of the following EDI_Exp_Desadv is validated
      | EDI_Exp_Desadv_ID.Identifier | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerTU | OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerLU | OPT.EDI_Exp_Desadv_Pack_Item.QtyTU |
      | e_dB_150                     | 20                                       | 100                                      | 5                                  |

    # ─── RECIPIENT-GLN ASSERTION (me03 #30308) ────────────────────────────────
    # Both replication-interface DESADV XMLs must carry the recipient GLN under <C_BPartner_ID>,
    # resolved from bp_150's partner-default C_BPartner_EDI_Setting row (edi_setting_S150_bp1).
    # Regression: the element vanished when #30238 dropped the C_BPartner GLN column without
    # repointing the replication (EXP_Format) export path.
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | OPT.UnderTag  | TagName         | OPT.Value     |
      | e_dA_150                     | C_BPartner_ID | EdiRecipientGLN | 1234567890123 |
      | e_dB_150                     | C_BPartner_ID | EdiRecipientGLN | 1234567890123 |

  # ─── S31978_TC1 — attribute-less ASI on both sides ────────────────────────────────────────────
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @allure.label.feature:F00353_EDI_DESADV
  @Id:S31978_TC1
  Scenario: S31978_TC1 — Attribute-less ASI on the product-identifier record and on the order line still resolves the buyer's CU identifiers
  ## Regression for the ASI wildcard-matching bug: an M_Product_ASI_Data record whose own ASI carries
  ## an attribute (Lot-Nummer) with no value, matched against an order line whose ASI also carries
  ## that same attribute with no value, must still be treated as a match (both ASI keys resolve to
  ## NONE). The product itself has no GTIN/UPC, so a lost match ships the DESADV line with empty
  ## CU identifiers instead of the buyer's ProductNo/GTIN_CU/EAN_CU.
  ## Overrides the Background's default chain: production runs OneDesadvPerShipment='N',
  ## which is the EXP_Format 540405 (EDI_Exp_Desadv) chain — the same one S29231_150 uses.
    Given metasfresh is configured for One-DESADV-Per-Shipment
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |

    # Explicit, distinct Value/Name (not auto-generated) so the CORE ASSERTION below can tell
    # which source the exported ProductNo actually came from: EDI_DesadvLine.ProductNo,
    # M_Product.Value, M_Product.Name and EDI_DesadvLine.ProductDescription must each render
    # as a different string. GTIN/UPC stay empty on purpose — no fallback identifier available.
    And metasfresh contains M_Products:
      | Identifier | Value            | Name            |
      | product    | PRODVALUE-S31978 | PRODNAME-S31978 |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | buyer      | Y          | pricingSystem      | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | buyer         | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier |
      | buyer         | true                 | 1234567890123         | ediSetting |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | expProcessorType                 | RabbitMQ |
    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name                      | EXP_Processor_Type_ID.Identifier |
      | expProcessor                | ediExportAttributelessASI | expProcessorType                 |
    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name                     | EntityType | EXP_Processor_ID.Identifier |
      | replicationStrategy                  | rabbitMQAttributelessASI | U          | expProcessor                |
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | replicationStrategy                  |
    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue            |
      | expProcessor                | exchangeName   | ediExportAttributelessASI |
      | expProcessor                | routingKey     | ediExportAttributelessASI |
      | expProcessor                | isDurableQueue | true                      |

    And metasfresh contains M_AttributeSetInstance with identifier "asiOnProductData":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer" }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnOrderLine":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer" }
      ]
    }
    """

    # The buyer's identifiers for this product, keyed to an attribute-less ASI (production's shape).
    And metasfresh contains M_Product_ASI_Data:
      | Identifier     | M_Product_ID.Identifier | C_BPartner_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | SeqNo | ProductNo  | GTIN          | EAN_CU        |
      | productAsiData | product                 | buyer                    | asiOnProductData                         | 10    | BUYER-9001 | 4012345678901 | 4012345678902 |

    # A real DESADV describes PACKED goods: a TU packing item wrapped by an LU, so the shipment's
    # line is picked up by the pack export source rather than falling through to the unpacked view.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPiLU     |
      | huPiTU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 1   | HU       | huPiTU                |
      | huPiItemTU      | huPiVersionTU      | 0   | PM       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | huPiItemProduct         | huPiItemTU      | product      | 10  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order      | true    | buyer                    | 2021-04-17  | PO_S31978_TC1   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine  | order                 | product                 | 10         | huPiItemProduct                        | asiOnOrderLine                           |

    When the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                 | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                 | shipment              |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | desadv                   | buyer                    | order                 |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | desadv                   |

    # Wait for D (SendingStarted) — this scenario's own Given overrides the Background to
    # OneDesadvPerShipment='N' (the 540405/EDI_Exp_Desadv pipeline, same as S29231_150 below), so
    # DesadvBL.isOneDesadvPerShipment(desadv) is false and recomputeDesadvStatusFromInOuts is
    # SKIPPED for this DESADV — the header status is never overwritten to Pending. D is therefore
    # both reachable and stable, and it is the point at which the RabbitMQ queue is actually
    # declared (Spring AMQP declares it on the first publish, in sendAMQPMessage()); U only means
    # "enqueued, not yet sent" and the next step polls that queue, so waiting for D avoids the same
    # queue-not-yet-declared race documented at S29231_150 below.
    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | desadv                   | D                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | expDesadv                    | expProcessor                | routingKey                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The buyer's ProductNo/GTIN_CU/EAN_CU must resolve even though both the M_Product_ASI_Data
    # record's own ASI and the order line's ASI are attribute-less; the product carries no GTIN/UPC
    # of its own, so a lost match ships these identifiers empty.
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | TagName   | OPT.Value     |
      | expDesadv                    | ProductNo | BUYER-9001    |
      | expDesadv                    | GTIN_CU   | 4012345678901 |
      | expDesadv                    | EAN_CU    | 4012345678902 |

  # ─── S31978_TC2 — a true wildcard outranks an attribute-less-conditional record ────────────────
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @allure.label.feature:F00353_EDI_DESADV
  @Id:S31978_TC2
  Scenario: S31978_TC2 — A wildcard M_Product_ASI_Data record at the lower SeqNo still wins over an attribute-less-conditional record
  ## Regression for the ASI wildcard-matching fix: a TRUE wildcard (no M_AttributeSetInstance_ID at
  ## all) and an attribute-less-conditional record (an ASI reference whose ASI carries no attribute
  ## value) both match any order line, but only the wildcard's own match is unconditional. Placing
  ## the wildcard at the lower SeqNo must still pick the wildcard, unchanged from before the fix —
  ## proving the fix did not disturb existing wildcard precedence.
  ## Overrides the Background's default chain: production runs OneDesadvPerShipment='N', which is
  ## the EXP_Format 540405 (EDI_Exp_Desadv) chain — the same one S31978_TC1 uses.
    Given metasfresh is configured for One-DESADV-Per-Shipment
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |

    # No explicit Value/Name: unlike TC1, this scenario never needs to distinguish the product
    # master's own identifiers from M_Product_ASI_Data — only the two ASI-data records compete.
    # GTIN/UPC stay empty on purpose — no fallback identifier available.
    And metasfresh contains M_Products:
      | Identifier |
      | product    |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | buyer      | Y          | pricingSystem      | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | buyer         | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier |
      | buyer         | true                 | 1234567890123         | ediSetting |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | expProcessorType                 | RabbitMQ |
    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name                        | EXP_Processor_Type_ID.Identifier |
      | expProcessor                | ediExportWildcardPrecedence | expProcessorType                 |
    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name                       | EntityType | EXP_Processor_ID.Identifier |
      | replicationStrategy                  | rabbitMQWildcardPrecedence | U          | expProcessor                |
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | replicationStrategy                  |
    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue              |
      | expProcessor                | exchangeName   | ediExportWildcardPrecedence |
      | expProcessor                | routingKey     | ediExportWildcardPrecedence |
      | expProcessor                | isDurableQueue | true                        |

    # The conditional record's own ASI carries an attribute with no value (attribute-less) — a
    # wildcard match only once matched against an order line, per the fix.
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnProductData":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer" }
      ]
    }
    """

    # wildcardAsiData has NO M_AttributeSetInstance_ID at all (a true wildcard) and the lower SeqNo;
    # conditionalAsiData references the attribute-less ASI above and sits at the higher SeqNo.
    # Distinct SeqNos + distinct identifier values so the assertion below can only pass if the
    # wildcard (not the conditional record, not arbitrary DB order) is the one that won.
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID.Identifier | C_BPartner_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | SeqNo | ProductNo    | GTIN          | EAN_CU        |
      | wildcardAsiData    | product                 | buyer                    |                                          | 10    | WILDCARD-001 | 4000000000010 | 4000000000011 |
      | conditionalAsiData | product                 | buyer                    | asiOnProductData                         | 20    | COND-002     | 4000000000020 | 4000000000021 |

    # A real DESADV describes PACKED goods: a TU packing item wrapped by an LU, so the shipment's
    # line is picked up by the pack export source rather than falling through to the unpacked view.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPiLU     |
      | huPiTU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 1   | HU       | huPiTU                |
      | huPiItemTU      | huPiVersionTU      | 0   | PM       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | huPiItemProduct         | huPiItemTU      | product      | 10  |

    # The order line carries no ASI at all — a real, common case, and the one that lets the
    # wildcard's unconditional match settle this scenario regardless of the fix.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order      | true    | buyer                    | 2021-04-17  | PO_S31978_TC2   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | orderLine  | order                 | product                 | 10         | huPiItemProduct                        |

    When the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                 | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                 | shipment              |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | desadv                   | buyer                    | order                 |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | desadv                   |

    # Wait for D (SendingStarted) — same reasoning as S31978_TC1: this scenario's own Given
    # overrides the Background to OneDesadvPerShipment='N', so recomputeDesadvStatusFromInOuts is
    # skipped and D is both reachable and stable, and it is the point at which the RabbitMQ queue
    # is declared (Spring AMQP declares it on the first publish, in sendAMQPMessage()).
    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | desadv                   | D                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | expDesadv                    | expProcessor                | routingKey                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The wildcard's identifiers must win because it sits at the lower SeqNo — regardless of the
    # fix, since a true wildcard (no M_AttributeSetInstance_ID) has always matched unconditionally.
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | TagName   | OPT.Value     |
      | expDesadv                    | ProductNo | WILDCARD-001  |
      | expDesadv                    | GTIN_CU   | 4000000000010 |
      | expDesadv                    | EAN_CU    | 4000000000011 |

  # ─── S31978_TC9 — an attribute-less-conditional record at the lower SeqNo displaces the wildcard ─
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @allure.label.feature:F00353_EDI_DESADV
  @Id:S31978_TC9
  Scenario: S31978_TC9 — An attribute-less-conditional M_Product_ASI_Data record at the lower SeqNo displaces the wildcard
  ## Regression for the ASI wildcard-matching fix: before the fix, an attribute-less-conditional
  ## record (an ASI reference whose ASI carries no attribute value) never matched an order line with
  ## NO ASI at all, because the old code checked "line has no ASI" before checking "candidate is
  ## attribute-less". So the wildcard (which always matches) won regardless of SeqNo. After the fix,
  ## the attribute-less-conditional record is recognised as a wildcard match too, and — sitting at
  ## the lower SeqNo — it now correctly displaces the true wildcard.
  ## The order line carries NO attribute values: with real values on the line the conditional record
  ## already matched before the fix too (proving nothing) — see ProductASIDataRepository.matchesASI.
  ## Overrides the Background's default chain: production runs OneDesadvPerShipment='N', which is
  ## the EXP_Format 540405 (EDI_Exp_Desadv) chain — the same one S31978_TC1 uses.
    Given metasfresh is configured for One-DESADV-Per-Shipment
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |

    # No explicit Value/Name — only the two ASI-data records compete in this scenario.
    # GTIN/UPC stay empty on purpose — no fallback identifier available.
    And metasfresh contains M_Products:
      | Identifier |
      | product    |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | buyer      | Y          | pricingSystem      | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | buyer         | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier |
      | buyer         | true                 | 1234567890123         | ediSetting |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | expProcessorType                 | RabbitMQ |
    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name                             | EXP_Processor_Type_ID.Identifier |
      | expProcessor                | ediExportConditionalDisplacement | expProcessorType                 |
    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name                            | EntityType | EXP_Processor_ID.Identifier |
      | replicationStrategy                  | rabbitMQConditionalDisplacement | U          | expProcessor                |
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | replicationStrategy                  |
    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue                   |
      | expProcessor                | exchangeName   | ediExportConditionalDisplacement |
      | expProcessor                | routingKey     | ediExportConditionalDisplacement |
      | expProcessor                | isDurableQueue | true                             |

    # The conditional record's own ASI carries an attribute with no value (attribute-less).
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnProductData":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer" }
      ]
    }
    """

    # conditionalAsiData references the attribute-less ASI above and sits at the LOWER SeqNo;
    # wildcardAsiData has NO M_AttributeSetInstance_ID at all (a true wildcard) and the higher
    # SeqNo. Distinct SeqNos + distinct identifier values so the assertion below can only pass if
    # the conditional record (not the wildcard, not arbitrary DB order) is the one that won.
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID.Identifier | C_BPartner_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | SeqNo | ProductNo | GTIN          | EAN_CU        |
      | conditionalAsiData | product                 | buyer                    | asiOnProductData                         | 10    | COND-101  | 4000000000101 | 4000000000102 |
      | wildcardAsiData    | product                 | buyer                    |                                          | 20    | WILD-201  | 4000000000201 | 4000000000202 |

    # A real DESADV describes PACKED goods: a TU packing item wrapped by an LU, so the shipment's
    # line is picked up by the pack export source rather than falling through to the unpacked view.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPiLU     |
      | huPiTU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 1   | HU       | huPiTU                |
      | huPiItemTU      | huPiVersionTU      | 0   | PM       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | huPiItemProduct         | huPiItemTU      | product      | 10  |

    # The order line carries no ASI at all — no attribute values — which is what forces the
    # pre-fix code down the "line has no ASI → mismatch" branch for the conditional record.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order      | true    | buyer                    | 2021-04-17  | PO_S31978_TC9   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | orderLine  | order                 | product                 | 10         | huPiItemProduct                        |

    When the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                 | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                 | shipment              |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | desadv                   | buyer                    | order                 |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | desadv                   |

    # Wait for D (SendingStarted) — same reasoning as S31978_TC1: this scenario's own Given
    # overrides the Background to OneDesadvPerShipment='N', so recomputeDesadvStatusFromInOuts is
    # skipped and D is both reachable and stable, and it is the point at which the RabbitMQ queue
    # is declared (Spring AMQP declares it on the first publish, in sendAMQPMessage()).
    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | desadv                   | D                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | expDesadv                    | expProcessor                | routingKey                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The conditional record's identifiers must win: it sits at the lower SeqNo, and the fix makes
    # its attribute-less ASI match the order line's ASI-less line unconditionally.
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | TagName   | OPT.Value     |
      | expDesadv                    | ProductNo | COND-101      |
      | expDesadv                    | GTIN_CU   | 4000000000101 |
      | expDesadv                    | EAN_CU    | 4000000000102 |

  # ─── S31978_TC3 — a conditional record with a real attribute value matches a containing line ────
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @allure.label.feature:F00353_EDI_DESADV
  @Id:S31978_TC3
  Scenario: S31978_TC3 — A conditional M_Product_ASI_Data record with a real attribute value matches an order line whose ASI contains that value
  ## Regression guard for the ASI wildcard-matching fix: an M_Product_ASI_Data record whose own ASI
  ## carries a REAL attribute value (not attribute-less) must still match an order line whose ASI
  ## carries that same value — the content-based subset check (AttributesKeys.contains()) holds when
  ## both sides carry the identical attribute+value pair. The product itself has no GTIN/UPC, so a
  ## lost match ships the DESADV line with empty CU identifiers instead of the buyer's own.
  ## Overrides the Background's default chain: production runs OneDesadvPerShipment='N', which is
  ## the EXP_Format 540405 (EDI_Exp_Desadv) chain — the same one S31978_TC1 uses.
    Given metasfresh is configured for One-DESADV-Per-Shipment
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |

    # Explicit, distinct Value/Name so the CORE ASSERTION below can tell which source the exported
    # ProductNo actually came from. GTIN/UPC stay empty on purpose — no fallback identifier available.
    And metasfresh contains M_Products:
      | Identifier | Value                | Name                |
      | product    | PRODVALUE-S31978-TC3 | PRODNAME-S31978-TC3 |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | buyer      | Y          | pricingSystem      | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | buyer         | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier |
      | buyer         | true                 | 1234567890123         | ediSetting |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | expProcessorType                 | RabbitMQ |
    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name                         | EXP_Processor_Type_ID.Identifier |
      | expProcessor                | ediExportAttributeValueMatch | expProcessorType                 |
    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name                        | EntityType | EXP_Processor_ID.Identifier |
      | replicationStrategy                  | rabbitMQAttributeValueMatch | U          | expProcessor                |
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | replicationStrategy                  |
    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue               |
      | expProcessor                | exchangeName   | ediExportAttributeValueMatch |
      | expProcessor                | routingKey     | ediExportAttributeValueMatch |
      | expProcessor                | isDurableQueue | true                         |

    # Both sides carry the SAME real attribute value — the record's ASI is a subset of the line's ASI.
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnProductData":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer", "valueStr":"LOT2024001" }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnOrderLine":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer", "valueStr":"LOT2024001" }
      ]
    }
    """

    # The buyer's identifiers for this product, keyed to a conditional ASI carrying a real value.
    And metasfresh contains M_Product_ASI_Data:
      | Identifier     | M_Product_ID.Identifier | C_BPartner_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | SeqNo | ProductNo  | GTIN          | EAN_CU        |
      | productAsiData | product                 | buyer                    | asiOnProductData                         | 10    | BUYER-9301 | 4013000000301 | 4013000000302 |

    # A real DESADV describes PACKED goods: a TU packing item wrapped by an LU, so the shipment's
    # line is picked up by the pack export source rather than falling through to the unpacked view.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPiLU     |
      | huPiTU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 1   | HU       | huPiTU                |
      | huPiItemTU      | huPiVersionTU      | 0   | PM       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | huPiItemProduct         | huPiItemTU      | product      | 10  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order      | true    | buyer                    | 2021-04-17  | PO_S31978_TC3   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine  | order                 | product                 | 10         | huPiItemProduct                        | asiOnOrderLine                           |

    When the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                 | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                 | shipment              |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | desadv                   | buyer                    | order                 |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | desadv                   |

    # Wait for D (SendingStarted) — same reasoning as S31978_TC1: this scenario's own Given overrides
    # the Background to OneDesadvPerShipment='N', so recomputeDesadvStatusFromInOuts is skipped and D
    # is both reachable and stable, and it is the point at which the RabbitMQ queue is declared.
    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | desadv                   | D                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | expDesadv                    | expProcessor                | routingKey                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The buyer's ProductNo/GTIN_CU/EAN_CU must resolve from the conditional record: its own ASI
    # carries a real attribute value, and the order line's ASI carries that same value.
    And the following EDI_Exp_Desadv XML carries the expected elements:
      | EDI_Exp_Desadv_ID.Identifier | TagName   | OPT.Value     |
      | expDesadv                    | ProductNo | BUYER-9301    |
      | expDesadv                    | GTIN_CU   | 4013000000301 |
      | expDesadv                    | EAN_CU    | 4013000000302 |

  # ─── S31978_TC4 — a conditional record does NOT match a line whose ASI carries a different value ─
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @allure.label.feature:F00353_EDI_DESADV
  @Id:S31978_TC4
  Scenario: S31978_TC4 — A conditional M_Product_ASI_Data record does NOT match an order line whose ASI carries a different value for the same attribute
  ## Regression guard proving the fix has NOT become "always match": the conditional record's own ASI
  ## carries a real attribute value, and the order line's ASI carries a DIFFERENT value for that same
  ## attribute — the content-based subset check (AttributesKeys.contains()) must FAIL, so the record
  ## must NOT be used. Like S31978_TC1/TC3, the product carries no GTIN/UPC of its own, so a rejected
  ## match has no fallback identifier to fall back to — the CORE ASSERTION below proves ProductNo,
  ## GTIN_CU and EAN_CU are all ABSENT from the exported line, never the rejected record's values.
  ## Overrides the Background's default chain: production runs OneDesadvPerShipment='N', which is
  ## the EXP_Format 540405 (EDI_Exp_Desadv) chain — the same one S31978_TC1 uses.
    Given metasfresh is configured for One-DESADV-Per-Shipment
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |

    # Explicit, distinct Value/Name. No GTIN/UPC on the product (same shape as TC1/TC3, and the
    # customer's own masterdata) — a rejected match must leave the CU identifiers with no fallback.
    And metasfresh contains M_Products:
      | Identifier | Value                | Name                |
      | product    | PRODVALUE-S31978-TC4 | PRODNAME-S31978-TC4 |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | buyer      | Y          | pricingSystem      | 1234567890123 |
    And the following c_bpartner is changed
      | C_BPartner_ID | DeliveryRule |
      | buyer         | F            |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier |
      | buyer         | true                 | 1234567890123         | ediSetting |

    And load EXP_Processor_Type
      | EXP_Processor_Type_ID.Identifier | Value    |
      | expProcessorType                 | RabbitMQ |
    And metasfresh contains Exp_Processor
      | EXP_Processor_ID.Identifier | Name                            | EXP_Processor_Type_ID.Identifier |
      | expProcessor                | ediExportAttributeValueMismatch | expProcessorType                 |
    And metasfresh contains AD_Replication_Strategy
      | AD_ReplicationStrategy_ID.Identifier | Name                           | EntityType | EXP_Processor_ID.Identifier |
      | replicationStrategy                  | rabbitMQAttributeValueMismatch | U          | expProcessor                |
    And update AD_Client
      | AD_Client_ID.Identifier | AD_ReplicationStrategy_ID.Identifier |
      | 1000000                 | replicationStrategy                  |
    And update EXP_ProcessorParameter for the following EXP_Processor
      | EXP_Processor_ID.Identifier | Value          | ParameterValue                  |
      | expProcessor                | exchangeName   | ediExportAttributeValueMismatch |
      | expProcessor                | routingKey     | ediExportAttributeValueMismatch |
      | expProcessor                | isDurableQueue | true                            |

    # The record's own ASI carries a real value; the order line's ASI carries a DIFFERENT value for
    # the same attribute — the line's ASI is NOT a superset of the record's, so matchesASI() is false.
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnProductData":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer", "valueStr":"LOT2024001" }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asiOnOrderLine":
    """
    {
      "attributeInstances":[
        { "attributeCode":"Lot-Nummer", "valueStr":"LOT2024999" }
      ]
    }
    """

    # This record's identifiers must NOT appear on the exported line — the CORE ASSERTION below
    # proves ProductNo/GTIN_CU/EAN_CU are absent from the DESADV line, not these rejected values.
    And metasfresh contains M_Product_ASI_Data:
      | Identifier     | M_Product_ID.Identifier | C_BPartner_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | SeqNo | ProductNo  | GTIN          | EAN_CU        |
      | productAsiData | product                 | buyer                    | asiOnProductData                         | 10    | BUYER-9401 | 4013000000411 | 4013000000412 |

    # A real DESADV describes PACKED goods: a TU packing item wrapped by an LU, so the shipment's
    # line is picked up by the pack export source rather than falling through to the unpacked view.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPiLU     |
      | huPiTU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 1   | HU       | huPiTU                |
      | huPiItemTU      | huPiVersionTU      | 0   | PM       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | huPiItemProduct         | huPiItemTU      | product      | 10  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order      | true    | buyer                    | 2021-04-17  | PO_S31978_TC4   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine  | order                 | product                 | 10         | huPiItemProduct                        | asiOnOrderLine                           |

    When the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                 | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                 | shipment              |

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | desadv                   | buyer                    | order                 |

    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | desadv                   |

    # Wait for D (SendingStarted) — same reasoning as S31978_TC1: this scenario's own Given overrides
    # the Background to OneDesadvPerShipment='N', so recomputeDesadvStatusFromInOuts is skipped and D
    # is both reachable and stable, and it is the point at which the RabbitMQ queue is declared.
    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | desadv                   | D                |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | expDesadv                    | expProcessor                | routingKey                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # No match, and the product carries no GTIN/UPC of its own — so ProductNo, GTIN_CU and EAN_CU
    # have no fallback identifier and must be genuinely ABSENT from the XML (not merely empty), never
    # the rejected record's values (BUYER-9401 / 4013000000411 / 4013000000412).
    And the following EDI_Exp_Desadv XML does not carry the elements:
      | EDI_Exp_Desadv_ID.Identifier | TagName   |
      | expDesadv                    | ProductNo |
      | expDesadv                    | GTIN_CU   |
      | expDesadv                    | EAN_CU    |
