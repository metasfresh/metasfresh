@ghActions:run_on_executor5
Feature: EDI_DesadvPack and EDI_DesadvPack_Item, when the orderline has a normal UOM like PCE

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_010
  Scenario: S0316_010 - 1 Pack from 1 line with no HU & no packing item.
  There are no packing-infos to go with, so it assumes one LU, one TU and all CUs within that TU.
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

    Given metasfresh contains M_Products:
      | Identifier    |
      | p_1_S0316_010 |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0316_010 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0316_010 | ps_1_S0316_010     | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0316_010 | pl_1_S0316_010 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0316_010 | plv_1_S0316_010        | p_1_S0316_010 | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_S0316_010     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN |

    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | p_1_S0316_010           |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | o_1_S0316_010 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID  | QtyEntered |
      | ol_1_S0316_010 | o_1_S0316_010 | p_1_S0316_010 | 10         |

    When the order identified by o_1_S0316_010 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0316_010 | ol_1_S0316_010            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0316_010                  | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0316_010                  | s_1_S0316_010         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0316_010  | s_1_S0316_010         | p_1_S0316_010           | 10          | true      | ol_1_S0316_010                |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | p_1_S0316_010                 | true                | null                   | null                                 | null                     | 10        |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0316_010                     | p_1_S0316_010                 | 10              | 10              | 10                           | 10              | 10                           | 0                   | 1         | s_1_S0316_010             | shipmentLine_1_S0316_010      | null               | null          | null                                    | null                        |

    And the shipment identified by s_1_S0316_010 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_013
  Scenario: 1 Pack from 1 line with no HU & no packing item.
  There are no packing-infos to go with, so it assumes one LU, one TU and all CUs within that TU.
  StockUOM = PCE; InvoiceUOM = KGM
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

    Given metasfresh contains M_Products:
      | Identifier     |
      | p_1_11212023_4 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1_11212023_4          | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_1_11212023_4 |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_11212023_4 | ps_1_11212023_4    | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID  |
      | plv_1_11212023_4 | pl_1_11212023_4 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_11212023_4 | plv_1_11212023_4       | p_1_11212023_4 | 10.0     | KGM      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_11212023_4    |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer              | true                 | bPartnerDesadvRecipientGLN |

    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | p_1_11212023_4          |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | o_1_11212023_4 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_1_11212023_4 | o_1_11212023_4 | p_1_11212023_4 | 10         |

    When the order identified by o_1_11212023_4 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_11212023_4 | ol_1_11212023_4           | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_11212023_4                 | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_11212023_4                 | s_1_11212023_4        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_11212023_4 | s_1_11212023_4        | p_1_11212023_4          | 10          | true      | ol_1_11212023_4               |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | M_HU_ID | M_HU_PackagingCode_ID | GTIN_PackingMaterial | SeqNo |
      | p_1_11212023_4     | true                | null    | null                  | null                 | 10    |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_11212023_4                    | p_1_11212023_4                | 10              | 10              | 2.5                          | 10              | 2.5                          | 1         | s_1_11212023_4            | shipmentLine_1_11212023_4     | null               | null          | null                                    | null                        |

    And the shipment identified by s_1_11212023_4 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_016
  Scenario: 1 Pack from 1 line with no HU & no packing item.
  There are no packing-infos to go with, so it assumes one LU, one TU and all CUs within that TU.
  StockUOM = PCE; InvoiceUOM = KGM
  M_ProductPrice.InvoicableQtyBasedOn = CatchWeight
  C_OrderLine.QtyEntered = 10 PCE; M_ShipmentSchedule.QtyToDeliverCatch_Override = 3 KGM
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

    Given metasfresh contains M_Products:
      | Identifier     |
      | p_1_11212023_1 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1_11212023_1          | PCE                    | KGM                  | 0.25         | true                     |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_1_11212023_1 |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_11212023_1 | ps_1_11212023_1    | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID  |
      | plv_1_11212023_1 | pl_1_11212023_1 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID | InvoicableQtyBasedOn |
      | pp_1_11212023_1 | plv_1_11212023_1       | p_1_11212023_1 | 10.0     | KGM      | Normal           | CatchWeight          |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_11212023_1    |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer              | true                     | bPartnerDesadvRecipientGLN |

    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | p_1_11212023_1          |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | o_1_11212023_1 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_1_11212023_1 | o_1_11212023_1 | p_1_11212023_1 | 10         |

    When the order identified by o_1_11212023_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_11212023_1 | ol_1_11212023_1           | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override |
      | s_s_1_11212023_1                 | 3                              |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1_11212023_1                 |

    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1_11212023_1        | s_s_1_11212023_1                 | D                 | Y                  |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_11212023_1 | s_1_11212023_1        | p_1_11212023_1          | 10          | true      | ol_1_11212023_1               |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | M_HU_ID | M_HU_PackagingCode_ID | GTIN_PackingMaterial | SeqNo |
      | p_1_11212023_1     | true                | null    | null                  | null                 | 10    |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_11212023_1                    | p_1_11212023_1                | 10              | 10              | 3                            | 10              | 3                            | 1         | s_1_11212023_1            | shipmentLine_1_11212023_1     | null               | null          | null                                    | null                        |

    And the shipment identified by s_1_11212023_1 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_020
  Scenario: S0316_020 - 1 Pack from 1 line with no HU & 1 packing item.
  The packing-info with a capacity of 10 is created within the test and assigned to the order-line, so we expect the ordered qty to be distributed among 10 TUs.
  in:
  C_OrderLine:
  - QtyEntered = 100
  - M_HU_PI_Item_Product_ID = created in the test

    Given metasfresh contains M_Products:
      | Identifier    |
      | p_1_S0316_020 |
      | p_2_S0316_020 |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0316_020 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1_S0316_020 | ps_1_S0316_020     | DE           | EUR           | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0316_020 | pl_1_S0316_020 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0316_020 | plv_1_S0316_020        | p_1_S0316_020 | 10.0     | PCE      | Normal           |
      | pp_2_S0316_020 | plv_1_S0316_020        | p_2_S0316_020 | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer | Y          | ps_1_S0316_020                |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer              | true                     | bPartnerDesadvRecipientGLN |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN            |
      | bp_1_S0316_020                   | endcustomer              | p_2_S0316_020           | bPartnerProductGTIN |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                |
      | pm_1_S0316_020                     | p_2_S0316_020               | packingMaterialTest |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0316_020      | ISO1          | LU          |
      | huPackagingCode_2_S0316_020      | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID                   |
      | huPackingLU_S0316_020        |
      | huPackingTU_S0316_020        |
      | huPackingVirtualPI_S0316_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID         | M_HU_PI_ID                   | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0316_020 | huPackingLU_S0316_020        | LU          | Y         |                             |
      | packingVersionTU_S0316_020 | huPackingTU_S0316_020        | TU          | Y         | huPackagingCode_2_S0316_020 |
      | packingVersionCU_S0316_020 | huPackingVirtualPI_S0316_020 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU_S0316_020       | packingVersionLU_S0316_020    | 10  | HU       | huPackingTU_S0316_020            |                                        |
      | huPiItemTU_S0316_020       | packingVersionTU_S0316_020    | 0   | PM       |                                  | pm_1_S0316_020                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU_S0316_020         | huPiItemTU_S0316_020       | p_1_S0316_020           | 10  | 2021-01-01 | huPackagingCode_1_S0316_020                      | gtinPiItemProduct                    |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI_S0316_020":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"Lot-Nummer",
          "valueStr":"lotNumber"
      },
      {
        "attributeCode":"HU_BestBeforeDate",
          "valueStr":"2021-04-20"
      }
    ]
  }
  """

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | o_1_S0316_020 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID    | M_AttributeSetInstance_ID |
      | ol_1_S0316_020 | o_1_S0316_020 | p_1_S0316_020 | 100        | huAuditProductTU_S0316_020 | orderLineASI_S0316_020    |

    When the order identified by o_1_S0316_020 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0316_020 | ol_1_S0316_020            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0316_020                  | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0316_020                  | s_1_S0316_020         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0316_020  | s_1_S0316_020         | p_1_S0316_020           | 100         | true      | ol_1_S0316_020                |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID | M_HU_PackagingCode_ID       | GTIN_PackingMaterial | SeqNo |
      | p_1_S0316_020                 | true                | null        | huPackagingCode_1_S0316_020 | gtinPiItemProduct    | 10    |

    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0316_020                     | p_1_S0316_020                 | 100             | 10              | 10                           | 100             | 100                          | 10                  | 10        | s_1_S0316_020             | shipmentLine_1_S0316_020      | 2021-04-20         | lotNumber     | huPackagingCode_2_S0316_020             | bPartnerProductGTIN         |

    And the shipment identified by s_1_S0316_020 is reversed

    Then after not more than 60s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 60s, there are no records in EDI_Desadv_Pack


    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_030
  Scenario: S0316_030 - 1 Pack from 1 line with HU for entire qty.
  There are no packing-infos to go with, but an actual HU is picked, so we use the qtys from that HU.
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

  M_ShipmentSchedule
  - QtyPickList = 10

    Given metasfresh contains M_Products:
      | Identifier    |
      | p_1_S0316_030 |
      | p_2_S0316_030 |
      | p_3_S0316_030 |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0316_030 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0316_030 | ps_1_S0316_030     | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0316_030 | pl_1_S0316_030 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0316_030 | plv_1_S0316_030        | p_1_S0316_030 | 10.0     | PCE      | Normal           |
      | pp_2_S0316_030 | plv_1_S0316_030        | p_2_S0316_030 | 10.0     | PCE      | Normal           |
      | pp_3_S0316_030 | plv_1_S0316_030        | p_3_S0316_030 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_S0316_030     |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer              | true                     | bPartnerDesadvRecipientGLN |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0316_030      | ISO1          | LU          |
      | huPackagingCode_2_S0316_030      | CART          | TU          |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier        |
      | huPackingLU_S0316_030        |
      | huPackingTU_S0316_030        |
      | huPackingVirtualPI_S0316_030 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID                   | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0316_030    | huPackingLU_S0316_030        | LU          | Y         | huPackagingCode_1_S0316_030 |
      | packingVersionTU_S0316_030    | huPackingTU_S0316_030        | TU          | Y         | huPackagingCode_2_S0316_030 |
      | packingVersionCU_S0316_030    | huPackingVirtualPI_S0316_030 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_S0316_030       | packingVersionLU_S0316_030    | 10  | HU       | huPackingTU_S0316_030            |
      | huPiItemTU_S0316_030       | packingVersionTU_S0316_030    | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU_S0316_030              | huPiItemTU_S0316_030       | p_1_S0316_030           | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier     | MovementDate | DocumentNo     |
      | huProduct_inventory_S0316_030 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier     | M_InventoryLine_ID.Identifier     | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_S0316_030 | huProduct_inventoryLine_S0316_030 | p_1_S0316_030           | 0       | 10       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_S0316_030'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier     | M_HU_ID.Identifier  |
      | huProduct_inventoryLine_S0316_030 | createdCU_S0316_030 |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU_S0316_030 | 10    | huProductTU_S0316_030              | createdTU_S0316_030           | newCreatedCU_S0316_030        |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier  | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_S0316_030 | huProductTU_S0316_030                  |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU_S0316_030 | 1     | huPiItemLU_S0316_030       | createdLU_S0316_030       |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier  | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_S0316_030 | 1000017        | luLotNumber | S                  |
      | createdLU_S0316_030 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1_S0316_030                   | endcustomer              | p_2_S0316_030           | bPartnerProductGTIN_LU |
      | bp_2_S0316_030                   | endcustomer              | p_3_S0316_030           | bPartnerProductGTIN_TU |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                             |
      | pm_1_S0316_030                     | p_2_S0316_030               | packingMaterialTest_LU_S0316_030 |
      | pm_2_S0316_030                     | p_3_S0316_030               | packingMaterialTest_TU_S0316_030 |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier  | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_S0316_030    | createdLU_S0316_030 | huPiItemLU_S0316_030       | 10  | pm_1_S0316_030                     | PM           |
      | huPiItemTU_S0316_030    | createdTU_S0316_030 | huPiItemTU_S0316_030       | 10  | pm_2_S0316_030                     | PM           |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   | C_PaymentTerm_ID | deliveryRule |
      | o_1_S0316_030 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ | 1000012          | F            |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID  | QtyEntered |
      | ol_1_S0316_030 | o_1_S0316_030 | p_1_S0316_030 | 10         |

    When the order identified by o_1_S0316_030 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0316_030 | ol_1_S0316_030            | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU_S0316_030 | s_s_1_S0316_030                  | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier |
      | createdLU_S0316_030 | s_s_1_S0316_030                  |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_S0316_030'

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0316_030                  | PD           | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0316_030                  | s_1_S0316_030         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0316_030  | s_1_S0316_030         | p_1_S0316_030           | 10          | true      | ol_1_S0316_030                |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | M_HU_ID             | M_HU_PackagingCode_ID       | GTIN_PackingMaterial   | SeqNo |
      | p_1_S0316_030      | true                | createdLU_S0316_030 | huPackagingCode_1_S0316_030 | bPartnerProductGTIN_LU | 1     |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0316_030                     | p_1_S0316_030                 | 10              | 10              | 10              | 10                  | 1         | s_1_S0316_030             | shipmentLine_1_S0316_030      | 2021-04-20         | luLotNumber   | huPackagingCode_2_S0316_030             | bPartnerProductGTIN_TU      |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18     |
      | p_1_S0316_030                 | ipaSSCC18_13092022 |

    And generate csv file for sscc labels for 'p_1_S0316_030'
      | ReportDataLine                                                                                                                                  |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                  |
      | %END%                                                                                                                                           |
      | "1","ipaSSCC18_13092022","@o_1_S0316_030@","16.04.2021","","@p_1_S0316_030@","1","0","210420","luLotNumber","","","","","","","","","","","","" |

    And the shipment identified by s_1_S0316_030 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_033
  Scenario: 1 Pack from 1 line with HU for entire qty.
  There are no packing-infos to go with, but an actual HU is picked with actual weight, so we use the weight from that HU.
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

    Given metasfresh contains M_Products:
      | Identifier     | Weight |
      | p_1_11212023_2 | 0.25   |
      | p_2_11212023_2 |        |
      | p_3_11212023_2 |        |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1_11212023_2          | PCE                    | KGM                  | 0.25         | true                     |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_1_11212023_2 |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_11212023_2 | ps_1_11212023_2    | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID  |
      | plv_1_11212023_2 | pl_1_11212023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID | InvoicableQtyBasedOn |
      | pp_1_11212023_2 | plv_1_11212023_2       | p_1_11212023_2 | 10.0     | PCE      | Normal           | CatchWeight          |
      | pp_2_11212023_2 | plv_1_11212023_2       | p_2_11212023_2 | 10.0     | PCE      | Normal           |                      |
      | pp_3_11212023_2 | plv_1_11212023_2       | p_3_11212023_2 | 10.0     | PCE      | Normal           |                      |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_11212023_2    |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_11212023_2     | ISO1          | LU          |
      | huPackagingCode_2_11212023_2     | CART          | TU          |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier         |
      | huPackingLU_11212023_2        |
      | huPackingTU_11212023_2        |
      | huPackingVirtualPI_11212023_2 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID                    | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID        |
      | packingVersionLU_11212023_2   | huPackingLU_11212023_2        | LU          | Y         | huPackagingCode_1_11212023_2 |
      | packingVersionTU_11212023_2   | huPackingTU_11212023_2        | TU          | Y         | huPackagingCode_2_11212023_2 |
      | packingVersionCU_11212023_2   | huPackingVirtualPI_11212023_2 | V           | Y         |                              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_11212023_2      | packingVersionLU_11212023_2   | 10  | HU       | huPackingTU_11212023_2           |
      | huPiItemTU_11212023_2      | packingVersionTU_11212023_2   | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU_11212023_2             | huPiItemTU_11212023_2      | p_1_11212023_2          | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier      | MovementDate | DocumentNo     |
      | huProduct_inventory_11212023_2 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier      | M_InventoryLine_ID.Identifier      | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_11212023_2 | huProduct_inventoryLine_11212023_2 | p_1_11212023_2          | 0       | 10       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_11212023_2'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier      | M_HU_ID.Identifier   |
      | huProduct_inventoryLine_11212023_2 | createdCU_11212023_2 |

    And transform CU to new TUs
      | sourceCU.Identifier  | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU_11212023_2 | 12    | huProductTU_11212023_2             | createdTU_11212023_2          | newCreatedCU_11212023_2       |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier   | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_11212023_2 | huProductTU_11212023_2                 |

    And transform TU to new LUs
      | sourceTU.Identifier  | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU_11212023_2 | 1     | huPiItemLU_11212023_2      | createdLU_11212023_2      |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier   | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_11212023_2 | 1000017        | luLotNumber | S                  |
      | createdLU_11212023_2 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1_11212023_2                  | endcustomer              | p_2_11212023_2          | bPartnerProductGTIN_LU |
      | bp_2_11212023_2                  | endcustomer              | p_3_11212023_2          | bPartnerProductGTIN_TU |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID   |
      | pm_1_11212023_2         | p_2_11212023_2 |
      | pm_2_11212023_2         | p_3_11212023_2 |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier   | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_11212023_2   | createdLU_11212023_2 | huPiItemLU_11212023_2      | 10  | pm_1_11212023_2                    | PM           |
      | huPiItemTU_11212023_2   | createdTU_11212023_2 | huPiItemTU_11212023_2      | 10  | pm_2_11212023_2                    | PM           |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   | C_PaymentTerm_ID | deliveryRule |
      | o_1_11212023_2 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ | 1000012          | F            |

    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1_11212023_2 | o_1_11212023_2        | p_1_11212023_2          | 10         |

    When the order identified by o_1_11212023_2 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_11212023_2 | ol_1_11212023_2           | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier   | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU_11212023_2 | s_s_1_11212023_2                 | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier   | M_ShipmentSchedule_ID.Identifier |
      | createdLU_11212023_2 | s_s_1_11212023_2                 |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_11212023_2'

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_11212023_2                 | PD           | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_11212023_2                 | s_1_11212023_2        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_11212023_2 | s_1_11212023_2        | p_1_11212023_2          | 10          | true      | ol_1_11212023_2               |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | M_HU_ID              | M_HU_PackagingCode_ID        | GTIN_PackingMaterial   | SeqNo |
      | p_1_11212023_2                | true                | createdLU_11212023_2 | huPackagingCode_1_11212023_2 | bPartnerProductGTIN_LU | 1     |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_11212023_2                    | p_1_11212023_2                | 10              | 10              | 2.5                          | 10              | 2.5                          | 10                  | 1         | s_1_11212023_2            | shipmentLine_1_11212023_2     | luLotNumber   | huPackagingCode_2_11212023_2            | bPartnerProductGTIN_TU      |

    And the shipment identified by s_1_11212023_2 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_036
  Scenario: 1 Pack from 1 line with HU for entire qty.
  There are no packing-infos to go with, but an actual HU is picked with actual weight, so we use the weight from that HU and then we use the QtyToDeliverCatch_Override for the remaining qty
  in:
  C_OrderLine:
  - QtyEntered = 15
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)
  - Weight = 0.25 KGM

    Given metasfresh contains M_Products:
      | Identifier     | Weight |
      | p_1_11212023_3 | 0.25   |
      | p_2_11212023_3 |        |
      | p_3_11212023_3 |        |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1_11212023_3          | PCE                    | KGM                  | 0.25         | true                     |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_1_11212023_3 |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_11212023_3 | ps_1_11212023_3    | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID  |
      | plv_1_11212023_3 | pl_1_11212023_3 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID | InvoicableQtyBasedOn |
      | pp_1_11212023_3 | plv_1_11212023_3       | p_1_11212023_3 | 10.0     | PCE      | Normal           | CatchWeight          |
      | pp_2_11212023_3 | plv_1_11212023_3       | p_2_11212023_3 | 10.0     | PCE      | Normal           |                      |
      | pp_3_11212023_3 | plv_1_11212023_3       | p_3_11212023_3 | 10.0     | PCE      | Normal           |                      |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_11212023_3    |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID        | PackagingCode | HU_UnitType |
      | huPackagingCode_1_11212023_3 | ISO1          | LU          |
      | huPackagingCode_2_11212023_3 | CART          | TU          |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier         |
      | huPackingLU_11212023_3        |
      | huPackingTU_11212023_3        |
      | huPackingVirtualPI_11212023_3 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID                    | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID        |
      | packingVersionLU_11212023_3   | huPackingLU_11212023_3        | LU          | Y         | huPackagingCode_1_11212023_3 |
      | packingVersionTU_11212023_3   | huPackingTU_11212023_3        | TU          | Y         | huPackagingCode_2_11212023_3 |
      | packingVersionCU_11212023_3   | huPackingVirtualPI_11212023_3 | V           | Y         |                              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_11212023_3      | packingVersionLU_11212023_3   | 10  | HU       | huPackingTU_11212023_3           |
      | huPiItemTU_11212023_3      | packingVersionTU_11212023_3   | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU_11212023_3             | huPiItemTU_11212023_3      | p_1_11212023_3          | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier      | MovementDate | DocumentNo     |
      | huProduct_inventory_11212023_3 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier      | M_InventoryLine_ID.Identifier      | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_11212023_3 | huProduct_inventoryLine_11212023_3 | p_1_11212023_3          | 0       | 10       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_11212023_3'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier      | M_HU_ID.Identifier   |
      | huProduct_inventoryLine_11212023_3 | createdCU_11212023_3 |

    And transform CU to new TUs
      | sourceCU.Identifier  | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU_11212023_3 | 12    | huProductTU_11212023_3             | createdTU_11212023_3          | newCreatedCU_11212023_3       |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier   | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_11212023_3 | huProductTU_11212023_3                 |

    And transform TU to new LUs
      | sourceTU.Identifier  | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU_11212023_3 | 1     | huPiItemLU_11212023_3      | createdLU_11212023_3      |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier   | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_11212023_3 | 1000017        | luLotNumber | S                  |
      | createdLU_11212023_3 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1_11212023_3                  | endcustomer              | p_2_11212023_3          | bPartnerProductGTIN_LU |
      | bp_2_11212023_3                  | endcustomer              | p_3_11212023_3          | bPartnerProductGTIN_TU |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID   |
      | pm_1_11212023_3         | p_2_11212023_3 |
      | pm_2_11212023_3         | p_3_11212023_3 |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier   | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_11212023_3   | createdLU_11212023_3 | huPiItemLU_11212023_3      | 10  | pm_1_11212023_3                    | PM           |
      | huPiItemTU_11212023_3   | createdTU_11212023_3 | huPiItemTU_11212023_3      | 10  | pm_2_11212023_3                    | PM           |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   | C_PaymentTerm_ID | deliveryRule |
      | o_1_11212023_3 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ | 1000012          | F            |

    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_1_11212023_3 | o_1_11212023_3 | p_1_11212023_3 | 15         |

    When the order identified by o_1_11212023_3 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_11212023_3 | ol_1_11212023_3           | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier   | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU_11212023_3 | s_s_1_11212023_3                 | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier   | M_ShipmentSchedule_ID.Identifier |
      | createdLU_11212023_3 | s_s_1_11212023_3                 |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_11212023_3'

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override |
      | s_s_1_11212023_3                 | 1.25                           |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1_11212023_3                 |

    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1_11212023_3        | s_s_1_11212023_3                 | PD                | Y                  |

    Then after not more than 120s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | M_HU_ID              | M_HU_PackagingCode_ID        | GTIN_PackingMaterial   | SeqNo |
      | p_1_11212023_3     | true                | createdLU_11212023_3 | huPackagingCode_1_11212023_3 | bPartnerProductGTIN_LU | 10    |
      | p_1_11212023_4     | true                |                      |                              |                        | 20    |

    And after not more than 120s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_11212023_3                    | p_1_11212023_3                | 10              | 10              | 2.5                          | 10              | 2.5                          | 10                  | 1         | s_1_11212023_3            | luLotNumber   | huPackagingCode_2_11212023_3            | bPartnerProductGTIN_TU      |
      | pi_1_11212023_4                    | p_1_11212023_4                | 5               | 5               | 1.25                         | 5               | 1.25                         | 0                   | 1         | s_1_11212023_3            |               |                                         |                             |

    And the shipment identified by s_1_11212023_3 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S0316_040
  Scenario: S0316_040 - 2 Packs from 1 line with HU for partial qty & 1 packing item.
  The packing-info with a capacity of 10 is created within the test and assigned to the order-line, but then an HU with qty=5 is picked, before a shipment with the complete qty=10 is created. so we expect one "generic" EDI_Desadv_Pack_Item the is created from the packing-info and another one that reflects the actual HU.
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = created in the test

  M_ShipmentSchedule
  - QtyPickList = 5

    Given metasfresh contains M_Products:
      | Identifier    |
      | p_1_S0316_040 |
      | p_2_S0316_040 |
      | p_3_S0316_040 |
      | p_4_S0316_040 |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0316_040 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0316_040 | ps_1_S0316_040     | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier |
      | plv_1_S0316_040 | pl_1_S0316_040            |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0316_040 | plv_1_S0316_040        | p_1_S0316_040 | 10.0     | PCE      | Normal           |
      | pp_2_S0316_040 | plv_1_S0316_040        | p_2_S0316_040 | 10.0     | PCE      | Normal           |
      | pp_3_S0316_040 | plv_1_S0316_040        | p_3_S0316_040 | 10.0     | PCE      | Normal           |
      | pp_4_S0316_040 | plv_1_S0316_040        | p_4_S0316_040 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_S0316_040     |

    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN |

    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID       | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0316_040 | ISO1          | LU          |
      | huPackagingCode_2_S0316_040 | CART          | TU          |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID  |
      | pm_1_S0316_040          | p_2_S0316_040 |
      | pm_2_S0316_040          | p_3_S0316_040 |
      | pm_3_S0316_040          | p_4_S0316_040 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier          |
      | huPackingLU_S0316_040          |
      | huPackingTU_S0316_040          |
      | huPackingVirtualPI_S0316_040   |
      | huPackingLU_2_S0316_040        |
      | huPackingTU_2_S0316_040        |
      | huPackingVirtualPI_2_S0316_040 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID           | M_HU_PI_ID                     | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0316_040   | huPackingLU_S0316_040          | LU          | Y         | huPackagingCode_1_S0316_040 |
      | packingVersionTU_S0316_040   | huPackingTU_S0316_040          | TU          | Y         | huPackagingCode_2_S0316_040 |
      | packingVersionCU_S0316_040   | huPackingVirtualPI_S0316_040   | V           | Y         |                             |
      | packingVersionLU_2_S0316_040 | huPackingLU_2_S0316_040        | LU          | Y         |                             |
      | packingVersionTU_2_S0316_040 | huPackingTU_2_S0316_040        | TU          | Y         | huPackagingCode_2_S0316_040 |
      | packingVersionCU_2_S0316_040 | huPackingVirtualPI_2_S0316_040 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU_S0316_040       | packingVersionLU_S0316_040    | 10  | HU       | huPackingTU_S0316_040            |                                        |
      | huPiItemTU_S0316_040       | packingVersionTU_S0316_040    | 0   | MI       |                                  |                                        |
      | huPiItemLU_2_S0316_040     | packingVersionLU_2_S0316_040  | 10  | HU       | huPackingTU_2_S0316_040          |                                        |
      | huPiItemTU_2_S0316_040     | packingVersionTU_2_S0316_040  | 0   | PM       |                                  | pm_3_S0316_040                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huProductTU_S0316_040              | huPiItemTU_S0316_040       | p_1_S0316_040           | 10  | 2021-01-01 |                                                  |                                      |
      | huAuditProductTU_2_S0316_040       | huPiItemTU_2_S0316_040     | p_1_S0316_040           | 10  | 2021-01-01 | huPackagingCode_1_S0316_040                      | gtinPiItemProduct                    |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI_S0316_040":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"Lot-Nummer",
          "valueStr":"lotNumber"
      },
      {
        "attributeCode":"HU_BestBeforeDate",
          "valueStr":"2021-04-20"
      }
    ]
  }
  """

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier     | MovementDate | DocumentNo     |
      | huProduct_inventory_S0316_040 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier     | M_InventoryLine_ID.Identifier     | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_S0316_040 | huProduct_inventoryLine_S0316_040 | p_1_S0316_040           | 0       | 5        |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_S0316_040'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier     | M_HU_ID.Identifier  |
      | huProduct_inventoryLine_S0316_040 | createdCU_S0316_040 |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU_S0316_040 | 5     | huProductTU_S0316_040              | createdTU_S0316_040           | newCreatedCU_S0316_040        |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier  | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_S0316_040 | huProductTU_S0316_040                  |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU_S0316_040 | 1     | huPiItemLU_S0316_040       | createdLU_S0316_040       |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier  | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_S0316_040 | 1000017        | luLotNumber | S                  |
      | createdLU_S0316_040 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1_S0316_040                   | endcustomer              | p_2_S0316_040           | bPartnerProductGTIN_LU |
      | bp_2_S0316_040                   | endcustomer              | p_3_S0316_040           | bPartnerProductGTIN_TU |
      | bp_3_S0316_040                   | endcustomer              | p_4_S0316_040           | bPartnerProductGTIN    |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier  | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_S0316_040    | createdLU_S0316_040 | huPiItemLU_S0316_040       | 10  | pm_1_S0316_040                     | PM           |
      | huPiItemTU_S0316_040    | createdTU_S0316_040 | huPiItemTU_S0316_040       | 10  | pm_2_S0316_040                     | PM           |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   | C_PaymentTerm_ID | deliveryRule |
      | o_1_S0316_040 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ | 1000012          | F            |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID      | M_AttributeSetInstance_ID |
      | ol_1_S0316_040 | o_1_S0316_040 | p_1_S0316_040 | 10         | huAuditProductTU_2_S0316_040 | orderLineASI_S0316_040    |

    When the order identified by o_1_S0316_040 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0316_040 | ol_1_S0316_040            | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU_S0316_040 | s_s_1_S0316_040                  | 5         | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier |
      | createdLU_S0316_040 | s_s_1_S0316_040                  |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_S0316_040'

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0316_040                  | PD           | true                | false       |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | M_HU_ID             | M_HU_PackagingCode_ID       | GTIN_PackingMaterial   |
      | p_1_S0316_040                 | true                | null                | huPackagingCode_1_S0316_040 | gtinPiItemProduct      |
      | p_2_S0316_040                 | true                | createdLU_S0316_040 | huPackagingCode_1_S0316_040 | bPartnerProductGTIN_LU |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0316_040                     | p_1_S0316_040                 | 5               | 10              | 5               | 10                  | 1         | 2021-04-20         | lotNumber     | huPackagingCode_2_S0316_040             | bPartnerProductGTIN         |
      | pi_2_S0316_040                     | p_2_S0316_040                 | 5               | 5               | 5               | 5                   | 1         | 2021-04-20         | luLotNumber   | huPackagingCode_2_S0316_040             | bPartnerProductGTIN_TU      |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18       |
      | p_1_S0316_040                 | ipaSSCC18_14092022_1 |
      | p_2_S0316_040                 | ipaSSCC18_14092022_2 |

    And generate csv file for sscc labels for 'p_1_S0316_040,p_2_S0316_040'
      | ReportDataLine                                                                                                                                    |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                    |
      | %END%                                                                                                                                             |
      | "1","ipaSSCC18_14092022_1","@o_1_S0316_040@","16.04.2021","","@p_1_S0316_040@","1","0","210420","lotNumber","","","","","","","","","","","",""   |
      | "1","ipaSSCC18_14092022_2","@o_1_S0316_040@","16.04.2021","","@p_1_S0316_040@","1","0","210420","luLotNumber","","","","","","","","","","","","" |


  Scenario: S0457_010 - 1 Pack from 2 lines with HUs for entire qty.
  There are no packing-infos to go with, but the lines are picked onto two actual HUs, so we use the qtys from that HU.
  in:
  C_OrderLine 1:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)
  C_OrderLine 2:
  - QtyEntered = 20
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

  M_ShipmentSchedule 1:
  - QtyPickList = 10
  M_ShipmentSchedule 2:
  - QtyPickList = 20

    Given metasfresh contains M_Products:
      | Identifier                  |
      | p_1_S0457_010               |
      | p_2_S0457_010               |
      | p_3_S0457_010_LU_packingMat |
      | p_4_S0457_010_TU_packingMat |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0457_010 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0457_010 | ps_1_S0457_010     | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0457_010 | pl_1_S0457_010 |
    And metasfresh contains M_ProductPrices
      | Identifier                   | M_PriceList_Version_ID | M_Product_ID                | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0457_010               | plv_1_S0457_010        | p_1_S0457_010               | 10.0     | PCE      | Normal           |
      | pp_2_S0457_010               | plv_1_S0457_010        | p_2_S0457_010               | 10.0     | PCE      | Normal           |
      | pp_3_S0457_010_LU_packingMat | plv_1_S0457_010        | p_3_S0457_010_LU_packingMat | 10.0     | PCE      | Normal           |
      | pp_4_S0457_010_TU_packingMat | plv_1_S0457_010        | p_4_S0457_010_TU_packingMat | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1_S0457_010     |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer              | true                     | bPartnerDesadvRecipientGLN |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0457_010      | ISO1          | LU          |
      | huPackagingCode_2_S0457_010      | CART          | TU          |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier        |
      | huPackingLU_S0457_010        |
      | huPackingTU_S0457_010        |
      | huPackingVirtualPI_S0457_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID                   | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0457_010    | huPackingLU_S0457_010        | LU          | Y         | huPackagingCode_1_S0457_010 |
      | packingVersionTU_S0457_010    | huPackingTU_S0457_010        | TU          | Y         | huPackagingCode_2_S0457_010 |
      | packingVersionCU_S0457_010    | huPackingVirtualPI_S0457_010 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_S0457_010       | packingVersionLU_S0457_010    | 10  | HU       | huPackingTU_S0457_010            |
      | huPiItemTU_S0457_010       | packingVersionTU_S0457_010    | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Attribute.Identifier   | M_HU_PI_Version_ID.Identifier | M_Attribute.Value |
      | huPiAttribute_SSCC18_S0457_010 | packingVersionLU_S0457_010    | SSCC18            |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU_1_S0457_010            | huPiItemTU_S0457_010       | p_1_S0457_010           | 10  | 2021-01-01 |
      | huProductTU_2_S0457_010            | huPiItemTU_S0457_010       | p_2_S0457_010           | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier     | MovementDate | DocumentNo     |
      | huProduct_inventory_S0457_010 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier     | M_InventoryLine_ID.Identifier       | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_S0457_010 | huProduct_inventoryLine_1_S0457_010 | p_1_S0457_010           | 0       | 10       |
      | huProduct_inventory_S0457_010 | huProduct_inventoryLine_2_S0457_010 | p_2_S0457_010           | 0       | 20       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_S0457_010'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier       | M_HU_ID.Identifier    |
      | huProduct_inventoryLine_1_S0457_010 | createdCU_1_S0457_010 |
      | huProduct_inventoryLine_2_S0457_010 | createdCU_2_S0457_010 |

    And transform CU to new TUs
      | sourceCU.Identifier   | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier                   | OPT.resultedNewCUs.Identifier                         |
      | createdCU_1_S0457_010 | 10    | huProductTU_1_S0457_010            | createdTU_1_S0457_010                           | newcreatedCU_1_S0457_010                              |
      | createdCU_2_S0457_010 | 20    | huProductTU_2_S0457_010            | createdTU_2_1_S0457_010,createdTU_2_2_S0457_010 | newCreatedCU_2_1_S0457_010,newCreatedCU_2_2_S0457_010 |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier      | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_1_S0457_010   | huProductTU_1_S0457_010                |
      | createdTU_2_1_S0457_010 | huProductTU_2_S0457_010                |
      | createdTU_2_2_S0457_010 | huProductTU_2_S0457_010                |

     # This controls the SSCC18 value that such our LU and desc-pack get SSCC18-value 012345670010000005
    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And aggregate TUs to new LU
      | sourceTUs                                                             | newLUs              |
      | createdTU_1_S0457_010,createdTU_2_1_S0457_010,createdTU_2_2_S0457_010 | createdLU_S0457_010 |

    # cleanup; otherwise, all HUs with an SSCC18 will have the same SSCC18-value for the remainder of this test-run
    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And update M_HU_Attribute:
      | M_HU_ID.Identifier  | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_S0457_010 | 1000017        | luLotNumber | S                  |
      | createdLU_S0457_010 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier     | OPT.GTIN               |
      | bp_1_S0457_010                   | endcustomer              | p_3_S0457_010_LU_packingMat | bPartnerProductGTIN_LU |
      | bp_3_S0457_010_LU_packingMat     | endcustomer              | p_4_S0457_010_TU_packingMat | bPartnerProductGTIN_TU |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                             |
      | pm_1_S0457_010                     | p_3_S0457_010_LU_packingMat | packingMaterialTest_LU_S0457_010 |
      | pm_2_S0457_010                     | p_4_S0457_010_TU_packingMat | packingMaterialTest_TU_S0457_010 |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier      | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_S0457_010    | createdLU_S0457_010     | huPiItemLU_S0457_010       | 10  | pm_1_S0457_010                     | PM           |
      | huPiItemTU_S0457_010    | createdTU_1_S0457_010   | huPiItemTU_S0457_010       | 10  | pm_2_S0457_010                     | PM           |
      | huPiItemTU_2_S0457_010  | createdTU_2_1_S0457_010 | huPiItemTU_S0457_010       | 20  | pm_2_S0457_010                     | PM           |
      | huPiItemTU_2_S0457_010  | createdTU_2_2_S0457_010 | huPiItemTU_S0457_010       | 20  | pm_2_S0457_010                     | PM           |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   | C_PaymentTerm_ID | deliveryRule |
      | o_1_S0457_010 | true    | endcustomer   | 2021-04-17  | po_ref_@Date@ | 1000012          | F            |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID  | QtyEntered |
      | ol_1_S0457_010 | o_1_S0457_010 | p_1_S0457_010 | 10         |
      | ol_2_S0457_010 | o_1_S0457_010 | p_2_S0457_010 | 20         |

    When the order identified by o_1_S0457_010 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0457_010 | ol_1_S0457_010            | N             |
      | s_s_2_S0457_010 | ol_2_S0457_010            | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdTU_1_S0457_010   | s_s_1_S0457_010                  | 10        | IP     | P          | ?              |
      | createdTU_2_1_S0457_010 | s_s_2_S0457_010                  | 10        | IP     | P          | ?              |
      | createdTU_2_2_S0457_010 | s_s_2_S0457_010                  | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier      | M_ShipmentSchedule_ID.Identifier |
      | createdTU_1_S0457_010   | s_s_1_S0457_010                  |
      | createdTU_2_1_S0457_010 | s_s_2_S0457_010                  |
      | createdTU_2_2_S0457_010 | s_s_2_S0457_010                  |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_S0457_010'

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0457_010                  | PD           | true                | false       |
      | s_s_2_S0457_010                  | PD           | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0457_010                  | s_1_S0457_010         |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2_S0457_010                  | s_2_S0457_010         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0457_010  | s_1_S0457_010         | p_1_S0457_010           | 10          | true      | ol_1_S0457_010                |
      | shipmentLine_2_S0457_010  | s_2_S0457_010         | p_2_S0457_010           | 20          | true      | ol_2_S0457_010                |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | OPT.IsManual_IPA_SSCC18 | OPT.M_HU_ID         | OPT.M_HU_PackagingCode_ID   | OPT.GTIN_PackingMaterial | OPT.SeqNo | OPT.IPA_SSCC18     |
      | p_1_S0457_010      | false                   | createdLU_S0457_010 | huPackagingCode_1_S0457_010 | bPartnerProductGTIN_LU   | 10        | 012345670010000005 |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0457_010                     | p_1_S0457_010                 | 10              | 10              | 10              | 10                  | 1         | s_1_S0457_010             | shipmentLine_1_S0457_010      | 2021-04-20         | luLotNumber   | huPackagingCode_2_S0457_010             | bPartnerProductGTIN_TU      |
      | pi_2_S0457_010                     | p_1_S0457_010                 | 20              | 10              | 20              | 10                  | 2         | s_2_S0457_010             | shipmentLine_2_S0457_010      | 2021-04-20         | luLotNumber   | huPackagingCode_2_S0457_010             | bPartnerProductGTIN_TU      |

    And generate csv file for sscc labels for 'p_1_S0457_010'
      | ReportDataLine                                                                                                                                  |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                  |
      | %END%                                                                                                                                           |
      | "1","012345670010000005","@o_1_S0457_010@","16.04.2021","","@p_1_S0457_010@","1","0","210420","luLotNumber","","","","","","","","","","","","" |
      | "1","012345670010000005","@o_1_S0457_010@","16.04.2021","","@p_2_S0457_010@","2","0","210420","luLotNumber","","","","","","","","","","","","" |

    And the shipment identified by s_1_S0457_010 is reversed
    And the shipment identified by s_2_S0457_010 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack
