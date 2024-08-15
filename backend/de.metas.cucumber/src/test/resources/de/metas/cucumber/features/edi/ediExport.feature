@ghActions:run_on_executor5
Feature: EDI_cctop_invoic_v export format

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

  #   Convenience Salat 250g
    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | convenienceSalate       | 2005577          |

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

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN            | OPT.EAN_CU           |
      | bp_1                             | 2156425                  | convenienceSalate       | bPartnerProductGTIN | bPartnerProductEANCU |

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.GLN             |
      | 2205175                           | bPartnerLocationGLN |

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

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    Then enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | o_1                   | invoice_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference     | paymentTerm | processed | docStatus |
      | invoice_1               | 2156425                  | 2205175                           | po_ref_23062023 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
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
      | EDI_cctop_invoic_v_ID.Identifier | OPT.Buyer_GTIN_CU   | OPT.Buyer_EAN_CU     | OPT.Supplier_GTIN_CU | OPT.Buyer_GTIN_TU | OPT.GTIN        |
      | ic_1                             | bPartnerProductGTIN | bPartnerProductEANCU | productGTIN          | itemProductGTIN   | itemProductGTIN |

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

    And 'generate shipments' process is invoked
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
      | d_1                      | D                |

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
