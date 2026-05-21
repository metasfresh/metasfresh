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
      | C_BPartner_ID.Identifier | OPT.Name2 | OPT.VATaxID     | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  | OPT.EdiInvoicRecipientGLN  | OPT.IsEdiInvoicRecipient | OPT.DeliveryRule |
      | 2156425                  | name2     | bPartnerVaTaxID | true                     | bPartnerDesadvRecipientGLN | bPartnerInvoicRecipientGLN | true                     | F                |

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
      | C_BPartner_ID.Identifier | OPT.Name2 | OPT.VATaxID | OPT.EdiDesadvRecipientGLN | OPT.EdiInvoicRecipientGLN | OPT.IsEdiInvoicRecipient | OPT.DeliveryRule |
      | 2156425                  | null      | null        | null                      | null                      | false                    | null             |

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
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN | OPT.DeliveryRule |
      | 2156425                  | true                     | 1234567890123             | F                |

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

     #  Test Kunde 1
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN | OPT.DeliveryRule |
      | 2156425                  | false                    | null                      | null             |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  @Id:S29231_150
  Scenario: S29231_150 — Two orders, one consolidated shipment → both DESADVs exported via the Replication Interface (RPL) path
  ## me03#29231 — Regression guard for the legacy RPL path (Broken-Path #5 + #6).
  ## Two EDI-DESADV-recipient orders for the same BPartner with distinct POReferences
  ## are completed (creating one EDI_Desadv per order). Their shipment schedules are
  ## consolidated by 'generate shipments' into a single M_InOut whose C_Order_ID is null
  ## (the legacy interceptor null-trick because lines come from 2 different orders).
  ## After enqueuing BOTH DESADVs the RPL export path (EDI_DESADV_InOut_Export.doExport)
  ## must run for the consolidated shipment, reading via the M_InOut_Desadv_V view
  ## that T8 rewrote to enumerate per-source-DESADV via the EDI_Desadv_M_InOut junction.
  ## Before T8 the view joined on the single-FK winner so only one DESADV's view row
  ## was visible; after T8 both DESADVs are visible via the junction.
  ## Before T9 the catch block flipped status on the single-FK winner only; after T9
  ## the junction-driven loop flips status on every linked DESADV on RPL failure.
  ## This scenario uses the One-DESADV-Per-Shipment EXP_Format (EDI_Exp_Desadv,
  ## ID=540405) which reads from M_InOut_Desadv_V — the view T8 rewrote.
    Given metasfresh is configured for One-DESADV-Per-Shipment

  #   Test Kunde 1
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN | OPT.DeliveryRule |
      | 2156425                  | true                     | 1234567890123             | F                |

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.GLN       |
      | 2205175                           | 1234567890123 |

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

    # Order A — distinct POReference → its own EDI_Desadv at order-complete.
    # @Date@ suffix keeps POReferences unique across local repeat runs (DB pollution guard).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised | OPT.POReference        |
      | oA_150     | true    | 2156425                  | 2021-04-17  | 2021-04-18Z      | PO_A_S29231_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olA_150    | oA_150                | convenienceSalate       | 100        | 3010001                                |

    And the order identified by oA_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | dA_150                   | 2156425                  | oA_150                |

    # Order B — different POReference → its own distinct EDI_Desadv
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised | OPT.POReference        |
      | oB_150     | true    | 2156425                  | 2021-04-17  | 2021-04-18Z      | PO_B_S29231_150_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olB_150    | oB_150                | convenienceSalate       | 100        | 3010001                                |

    And the order identified by oB_150 is completed

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | dB_150                   | 2156425                  | oB_150                |

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

    # The two source schedules MUST share the same M_InOut and the same EDI_Desadv-via-junction
    # (T1's junction table populated by DesadvBL.addToDesadvCreateForInOutIfNotExist).
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ssB_150                          | io_150                |

    # Enqueue both DESADVs — each fires its own RPL export (EDI_DESADV_InOut_Export.doExport).
    # After T8 the view M_InOut_Desadv_V enumerates BOTH DESADVs for io_150 via the junction.
    And EDI_Desadv is enqueued for export
      | EDI_Desadv_ID.Identifier |
      | dA_150                   |
      | dB_150                   |

    # ─── CORE ASSERTION ───────────────────────────────────────────────────────
    # The RPL export emits one EDIFACT XML to RabbitMQ per processed DESADV.
    # We poll for both messages on the configured queue (ediExport_150 routing key).
    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | e_dA_150                     | ep_2                        | routingKey                   |

    And RabbitMQ receives a EDI_Exp_Desadv
      | EDI_Exp_Desadv_ID.Identifier | EXP_Processor_ID.Identifier | EXP_ProcessorParameter.Value |
      | e_dB_150                     | ep_2                        | routingKey                   |

     #  Test Kunde 1 — restore BPartner flag
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN | OPT.DeliveryRule |
      | 2156425                  | false                    | null                      | null             |
