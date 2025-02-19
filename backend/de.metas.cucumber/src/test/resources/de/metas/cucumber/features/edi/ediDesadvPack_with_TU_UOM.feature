@ghActions:run_on_executor5
Feature: EDI_DesadvPack and EDI_DesadvPack_Item, when the orderline has a TU-UOM like TU or COLI
  # Note: we have no tests with M_HU_PI_Item_Product_ID = 101 (No Packing Item), 
  # because an orderline with UOM=COLI implies that the package is specified. 
  # Otherwise, it's unclear how many CUs are ordered to go into the COLI.
  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs

  @Id:S0317_010
  Scenario: S0317_010 - 1 Pack from 1 line with no HU & 1 packing item
  in:
  C_OrderLine:
  - QtyEntered = 4COLI = 40 CUs
  - M_HU_PI_Item_Product_ID = created in the test

    Given metasfresh contains M_Products:
      | Identifier    | OPT.X12DE355 |
      | p_1_S0317_010 | PCE          |
      | p_2_S0317_010 | PCE          |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1_S0317_010           | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0317_010 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0317_010 | ps_1_S0317_010     | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0317_010 | pl_1_S0317_010 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_S0317_010 | plv_1_S0317_010        | p_1_S0317_010 | 10.0     | KGM               | Normal                        |
      | pp_2_S0317_010 | plv_1_S0317_010        | p_2_S0317_010 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier              | IsVendor | OPT.IsCustomer | M_PricingSystem_ID |
      | endcustomer_1_S0317_010 | N        | Y              | ps_1_S0317_010     |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1_S0317_010  | true                     | bPartnerDesadvRecipientGLN |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN            |
      | bp_1_S0317_010                   | endcustomer_1_S0317_010  | p_2_S0317_010           | bPartnerProductGTIN |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name           |
      | pm_1_S0317_010                     | p_2_S0317_010               | name_S0317_010 |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0317_010      | ISO1          | LU          |
      | huPackagingCode_2_S0317_010      | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | Identifier                   |
      | huPackingLU_S0317_010        |
      | huPackingTU_S0317_010        |
      | huPackingVirtualPI_S0317_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier        | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID.Identifier |
      | packingVersionLU_S0317_010    | huPackingLU_S0317_010        | LU          | Y         |                                      |
      | packingVersionTU_S0317_010    | huPackingTU_S0317_010        | TU          | Y         | huPackagingCode_2_S0317_010          |
      | packingVersionCU_S0317_010    | huPackingVirtualPI_S0317_010 | V           | Y         |                                      |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU_S0317_010       | packingVersionLU_S0317_010    | 10  | HU       | huPackingTU_S0317_010            |                                        |
      | huPiItemTU_S0317_010       | packingVersionTU_S0317_010    | 0   | PM       |                                  | pm_1_S0317_010                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU_S0317_010         | huPiItemTU_S0317_010       | p_1_S0317_010           | 10  | 2021-01-01 | huPackagingCode_1_S0317_010                      | gtinPiItemProduct                    |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1_S0317_010 | true    | endcustomer_1_S0317_010  | 2021-04-17  | po_ref_@Date@   |

    # Setting UOM=COLI; since the M_HU_PI_Item_Product_ID has a capacity of 10, we'll expect 40CUs to be shipped
    # Note that both UOM and QtyItemCapacity can'T be set in the UOM. They are both coming from C_OLCand, where the QtyItemCapacity may be taken from M_HU_PI_Item_Product_ID
    # It's not ideal that we need to set it explicitly here, but otherwise we run into different problems with model-interceptors and MOrderLine.beforeSafe().
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1_S0317_010 | o_1_S0317_010         | p_1_S0317_010           | 4          | huAuditProductTU_S0317_010             | COLI                  | 10                  |

    When the order identified by o_1_S0317_010 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0317_010 | ol_1_S0317_010            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0317_010                  | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0317_010                  | s_1_S0317_010         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0317_010  | s_1_S0317_010         | p_1_S0317_010           | 40          | true      | ol_1_S0317_010                |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | p_1_S0317_010                 | true                | null                   | huPackagingCode_1_S0317_010          | gtinPiItemProduct        | 1         |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0317_010                     | p_1_S0317_010                 | 40              | 10              | 2.5                          | 40              | 10                           | 10                  | 4         | s_1_S0317_010             | shipmentLine_1_S0317_010      | huPackagingCode_2_S0317_010             | bPartnerProductGTIN         |

    And the shipment identified by s_1_S0317_010 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

  @Id:S0317_020
  Scenario: S0317_020 - 2 Packs from 1 line with HU for partial qty & 1 packing item
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = created in the test

  M_ShipmentSchedule
  - QtyPickList = 5

    Given metasfresh contains M_Products:
      | Identifier    |
      | p_1_S0317_020 |
      | p_2_S0317_020 |
      | p_3_S0317_020 |
      | p_4_S0317_020 |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0317_020 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0317_020 | ps_1_S0317_020     | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0317_020 | pl_1_S0317_020 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_S0317_020 | plv_1_S0317_020        | p_1_S0317_020 | 10.0     | PCE               | Normal                        |
      | pp_2_S0317_020 | plv_1_S0317_020        | p_2_S0317_020 | 10.0     | PCE               | Normal                        |
      | pp_3_S0317_020 | plv_1_S0317_020        | p_3_S0317_020 | 10.0     | PCE               | Normal                        |
      | pp_4_S0317_020 | plv_1_S0317_020        | p_4_S0317_020 | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier              | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1_S0317_020 | N        | Y          | ps_1_S0317_020     |

    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1_S0317_020  | true                     | bPartnerDesadvRecipientGLN |

    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0317_020      | ISO1          | LU          |
      | huPackagingCode_2_S0317_020      | CART          | TU          |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name              |
      | pm_1_S0317_020                     | p_2_S0317_020               | name_LU_S0317_020 |
      | pm_2_S0317_020                     | p_3_S0317_020               | name_TU_S0317_020 |
      | pm_3_S0317_020                     | p_4_S0317_020               | name_S0317_020    |

    And metasfresh contains M_HU_PI:
      | Identifier                     |
      | huPackingLU_S0317_020          |
      | huPackingTU_S0317_020          |
      | huPackingVirtualPI_S0317_020   |
      | huPackingLU_2_S0317_020        |
      | huPackingTU_2_S0317_020        |
      | huPackingVirtualPI_2_S0317_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier          | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID.Identifier |
      | packingVersionLU_S0317_020    | huPackingLU_S0317_020          | LU          | Y         | huPackagingCode_1_S0317_020          |
      | packingVersionTU_S0317_020    | huPackingTU_S0317_020          | TU          | Y         | huPackagingCode_2_S0317_020          |
      | packingVersionCU_S0317_020    | huPackingVirtualPI_S0317_020   | V           | Y         |                                      |
      | packingVersionLU_2_S0317_020  | huPackingLU_2_S0317_020        | LU          | Y         |                                      |
      | packingVersionTU_2_S0317_020  | huPackingTU_2_S0317_020        | TU          | Y         | huPackagingCode_2_S0317_020          |
      | packingVersionCU_2_S0317_020  | huPackingVirtualPI_2_S0317_020 | V           | Y         |                                      |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU_S0317_020       | packingVersionLU_S0317_020    | 10  | HU       | huPackingTU_S0317_020            |                                        |
      | huPiItemTU_S0317_020       | packingVersionTU_S0317_020    | 0   | MI       |                                  |                                        |
      | huPiItemLU_2_S0317_020     | packingVersionLU_2_S0317_020  | 10  | HU       | huPackingTU_2_S0317_020          |                                        |
      | huPiItemTU_2_S0317_020     | packingVersionTU_2_S0317_020  | 0   | PM       |                                  | pm_3_S0317_020                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huProductTU_S0317_020              | huPiItemTU_S0317_020       | p_1_S0317_020           | 10  | 2021-01-01 |                                                  |                                      |
      | huAuditProductTU_2_S0317_020       | huPiItemTU_2_S0317_020     | p_1_S0317_020           | 10  | 2021-01-01 | huPackagingCode_1_S0317_020                      | gtinPiItemProduct                    |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI_S0317_020":
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
      | huProduct_inventory_S0317_020 | 2021-04-16   | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier     | M_InventoryLine_ID.Identifier     | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory_S0317_020 | huProduct_inventoryLine_S0317_020 | p_1_S0317_020           | 0       | 5        |
    And complete inventory with inventoryIdentifier 'huProduct_inventory_S0317_020'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier     | M_HU_ID.Identifier  |
      | huProduct_inventoryLine_S0317_020 | createdCU_S0317_020 |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU_S0317_020 | 5     | huProductTU_S0317_020              | createdTU_S0317_020           | newCreatedCU_S0317_020        |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier  | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU_S0317_020 | huProductTU_S0317_020                  |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU_S0317_020 | 1     | huPiItemLU_S0317_020       | createdLU_S0317_020       |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier  | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU_S0317_020 | 1000017        | luLotNumber | S                  |
      | createdLU_S0317_020 | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1_S0317_020                   | endcustomer_1_S0317_020  | p_2_S0317_020           | bPartnerProductGTIN_LU |
      | bp_2_S0317_020                   | endcustomer_1_S0317_020  | p_3_S0317_020           | bPartnerProductGTIN_TU |
      | bp_3_S0317_020                   | endcustomer_1_S0317_020  | p_4_S0317_020           | bPartnerProductGTIN    |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier  | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU_S0317_020    | createdLU_S0317_020 | huPiItemLU_S0317_020       | 10  | pm_1_S0317_020                     | PM           |
      | huPiItemTU_S0317_020    | createdTU_S0317_020 | huPiItemTU_S0317_020       | 10  | pm_2_S0317_020                     | PM           |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | deliveryRule |
      | o_1_S0317_020 | true    | endcustomer_1_S0317_020  | 2021-04-17  | po_ref_@Date@   | 1000012              | F            |

    # Setting UOM=COLI; since the M_HU_PI_Item_Product_ID has a capacity of 10, we'll expect 100CUs to be shipped
    # Note that both UOM and QtyItemCapacity can'T be set in the UOM. They are both coming from C_OLCand, where the QtyItemCapacity may be taken from M_HU_PI_Item_Product_ID
    # It's not ideal that we need to set it explicitly here, but otherwise we run into different problems with model-interceptors and MOrderLine.beforeSafe().
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1_S0317_020 | o_1_S0317_020         | p_1_S0317_020           | 10         | huAuditProductTU_2_S0317_020           | orderLineASI_S0317_020                   | COLI                  | 10                  |

    When the order identified by o_1_S0317_020 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0317_020 | ol_1_S0317_020            | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU_S0317_020 | s_s_1_S0317_020                  | 5         | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier  | M_ShipmentSchedule_ID.Identifier |
      | createdLU_S0317_020 | s_s_1_S0317_020                  |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1_S0317_020'

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0317_020                  | PD           | true                | false       |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | M_HU_ID             | M_HU_PackagingCode_ID       | GTIN_PackingMaterial   |
      | p_1_S0317_020      | true                | null                | huPackagingCode_1_S0317_020 | gtinPiItemProduct      |
      | p_2_S0317_020      | true                | createdLU_S0317_020 | huPackagingCode_1_S0317_020 | bPartnerProductGTIN_LU |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerTU_InInvoiceUOM | OPT.QtyCUsPerLU | OPT.QtyCUsPerLU_InInvoiceUOM | OPT.QtyItemCapacity | OPT.QtyTU | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0317_020                     | p_1_S0317_020                 | 95              | 10              | 10                           | 95              | 95                           | 10                  | 10        | 2021-04-20         | lotNumber     | huPackagingCode_2_S0317_020             | bPartnerProductGTIN         |
      | pi_2_S0317_020                     | p_2_S0317_020                 | 5               | 5               | 5                            | 5               | 5                            | 5                   | 1         | 2021-04-20         | luLotNumber   | huPackagingCode_2_S0317_020             | bPartnerProductGTIN_TU      |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18       |
      | p_1_S0317_020                 | ipaSSCC18_14092022_1 |
      | p_2_S0317_020                 | ipaSSCC18_14092022_2 |

    And generate csv file for sscc labels for 'p_1_S0317_020,p_2_S0317_020'
      | ReportDataLine                                                                                                                                                                 |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                                                 |
      | %END%                                                                                                                                                                          |
      | "1","ipaSSCC18_14092022_1","@o_1_S0317_020.orderPOReference@","16.04.2021","","@p_1_S0317_020.productName@","10","0","210420","lotNumber","","","","","","","","","","","",""  |
      | "1","ipaSSCC18_14092022_2","@o_1_S0317_020.orderPOReference@","16.04.2021","","@p_1_S0317_020.productName@","1","0","210420","luLotNumber","","","","","","","","","","","","" |


  @Id:S0317_030
  Scenario: S0317_030 - 3 Packs from 2 lines with no HU & 1 packing item - !both lines have the same product!
  in:
  M_HU_PI_Item_Product_ID: created in the test, with just 1 TU per LU
  C_OrderLine-1:
  - QtyEntered = 2COLI = 2CUs
  C_OrderLine-2:
  - QtyEntered = 1COLI = 1CU

    Given metasfresh contains M_Products:
      | Identifier    | OPT.X12DE355 |
      | p_1_S0317_030 | PCE          |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1_S0317_030           | PCE                    | KGM                  | 0.25         | true                     |
    And metasfresh contains M_PricingSystems
      | Identifier     |
      | ps_1_S0317_030 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_S0317_030 | ps_1_S0317_030     | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | plv_1_S0317_030 | pl_1_S0317_030 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | InvoicableQtyBasedOn |
      | pp_1_S0317_030 | plv_1_S0317_030        | p_1_S0317_030 | 10.0     | KGM               | Normal                        | CatchWeight          |
    And metasfresh contains C_BPartners:
      | Identifier              | IsVendor | OPT.IsCustomer | M_PricingSystem_ID |
      | endcustomer_1_S0317_030 | N        | Y              | ps_1_S0317_030     |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1_S0317_030  | true                     | bPartnerDesadvRecipientGLN |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN      |
      | bp_1_S0317_030                   | endcustomer_1_S0317_030  | p_1_S0317_030           | 2234567890123 |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name           |
      | pm_1_S0317_030                     | p_1_S0317_030               | name_S0317_030 |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0317_030      | ISO1          | LU          |
      | huPackagingCode_2_S0317_030      | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | Identifier                   |
      | huPackingLU_S0317_030        |
      | huPackingTU_S0317_030        |
      | huPackingVirtualPI_S0317_030 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier        | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID.Identifier |
      | packingVersionLU_S0317_030    | huPackingLU_S0317_030        | LU          | Y         |                                      |
      | packingVersionTU_S0317_030    | huPackingTU_S0317_030        | TU          | Y         | huPackagingCode_2_S0317_030          |
      | packingVersionCU_S0317_030    | huPackingVirtualPI_S0317_030 | V           | Y         |                                      |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU_S0317_030       | packingVersionLU_S0317_030    | 1   | HU       | huPackingTU_S0317_030            |                                        |
      | huPiItemTU_S0317_030       | packingVersionTU_S0317_030    | 0   | PM       |                                  | pm_1_S0317_030                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU_S0317_030         | huPiItemTU_S0317_030       | p_1_S0317_030           | 1   | 2021-01-01 | huPackagingCode_1_S0317_030                      | 1234567890123                        |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1_S0317_030 | true    | endcustomer_1_S0317_030  | 2021-04-17  | po_ref_@Date@   |

    # Setting UOM=COLI; since the M_HU_PI_Item_Product_ID has a capacity of 10, we'll expect 20CUs resp 10CUs to be shipped.
    # Note that both UOM and QtyItemCapacity can't be set in the UOM. They are both coming from C_OLCand, where the QtyItemCapacity may be taken from M_HU_PI_Item_Product_ID
    # It's not ideal that we need to set it explicitly here, but otherwise we run into different problems with model-interceptors and MOrderLine.beforeSafe().
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1_S0317_030 | o_1_S0317_030         | p_1_S0317_030           | 2          | huAuditProductTU_S0317_030             | COLI                  | 1                   |
      | ol_2_S0317_030 | o_1_S0317_030         | p_1_S0317_030           | 1          | huAuditProductTU_S0317_030             | COLI                  | 1                   |

    When the order identified by o_1_S0317_030 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0317_030 | ol_1_S0317_030            | N             |
      | s_s_2_S0317_030 | ol_2_S0317_030            | N             |

      ## set catch-weight values! note that they exceed the weight to be expected from the C_UOM_Conversion
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override |
      | s_s_1_S0317_030                  | 1.10                           |
      | s_s_2_S0317_030                  | 0.51                           |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1_S0317_030                  |
      | s_s_2_S0317_030                  |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1_S0317_030                  |
      | s_s_2_S0317_030                  |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0317_030                  | s_1_S0317_030         |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_S0317_030  | s_1_S0317_030         | p_1_S0317_030           | 2           | true      | ol_1_S0317_030                |
      | shipmentLine_2_S0317_030  | s_1_S0317_030         | p_1_S0317_030           | 1           | true      | ol_2_S0317_030                |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | p_1_S0317_030                 | true                | null                   | huPackagingCode_1_S0317_030          | 1234567890123            | 1         |
      | p_2_S0317_030                 | true                | null                   | huPackagingCode_1_S0317_030          | 1234567890123            | 2         |
      | p_3_S0317_030                 | true                | null                   | huPackagingCode_1_S0317_030          | 1234567890123            | 3         |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCUsPerTU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1_S0317_030                     | p_1_S0317_030                 | 1               | 1               | 1               | 1                   | 1         | s_1_S0317_030             | shipmentLine_1_S0317_030      | huPackagingCode_2_S0317_030             | 2234567890123               |
      | pi_2_S0317_030                     | p_2_S0317_030                 | 1               | 1               | 1               | 1                   | 1         | s_1_S0317_030             | shipmentLine_1_S0317_030      | huPackagingCode_2_S0317_030             | 2234567890123               |
      | pi_3_S0317_030                     | p_3_S0317_030                 | 1               | 1               | 1               | 1                   | 1         | s_1_S0317_030             | shipmentLine_2_S0317_030      | huPackagingCode_2_S0317_030             | 2234567890123               |

    And the shipment identified by s_1_S0317_030 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack
