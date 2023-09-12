Feature: EDI_DesadvPack and EDI_DesadvPack_Item, when the orderline has a normal UOM like PCE

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs

  Scenario: 1 Pack from 1 line with no HU & no packing item
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_13092022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_13092022_1 | pricing_system_value_13092022_1 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_13092022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_13092022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_13092022_1 | N            | Y              | ps_1                          |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1            | true                     | bPartnerDesadvRecipientGLN |

    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer_1            | p_1                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | s_1                   | p_1                     | 10          | true      | ol_1                          |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_LU_ID.Identifier | OPT.GTIN_LU_PackingMaterial |
      | p_1                           | true                | null                   | null                                    | null                        |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1                               | p_1                           | 10              | 10        | 10              | 0                   | 1         | s_1                       | shipmentLine_1                | null               | null          | null                                    | null                        |

    And the shipment identified by s_1 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

  Scenario: 1 Pack from 1 line with no HU & 1 packing item
  in:
  C_OrderLine:
  - QtyEntered = 100
  - M_HU_PI_Item_Product_ID = created in the test

    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | salesProduct_13092022_2    |
      | p_2        | packingMaterial_13092022_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_13092022_2 | pricing_system_value_13092022_2 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_13092022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_13092022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_13092022_2 | N            | Y              | ps_1                          |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1            | true                     | bPartnerDesadvRecipientGLN |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN            |
      | bp_1                             | endcustomer_1            | p_2                     | bPartnerProductGTIN |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                |
      | pm_1                               | p_2                         | packingMaterialTest |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1                | ISO1          | LU          |
      | huPackagingCode_2                | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                       |
      | huPackingLU           | huPackingLU_13092022_2     |
      | huPackingTU           | huPackingTU_13092022_2     |
      | huPackingVirtualPI    | No Packing Item_13092022_2 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                        | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID |
      | packingVersionLU              | huPackingLU           | packingVersionLU_13092022_2 | LU          | Y         |                           |
      | packingVersionTU              | huPackingTU           | packingVersionTU_13092022_2 | TU          | Y         | huPackagingCode_2         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item_13092022_2  | V           | Y         |                           |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |                                        |
      | huPiItemTU                 | packingVersionTU              | 0   | PM       |                                  | pm_1                                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU                   | huPiItemTU                 | p_1                     | 10  | 2021-01-01 | huPackagingCode_1                                | gtinPiItemProduct                    |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_13092022_2 |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 100        | huAuditProductTU                       | orderLineASI                             |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | s_1                   | p_1                     | 100         | true      | ol_1                          |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_LU_ID.Identifier | OPT.GTIN_LU_PackingMaterial |
      | p_1                           | true                | null                   | huPackagingCode_1                       | gtinPiItemProduct           |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1                               | p_1                           | 100             | 10        | 100             | 10                  | 10        | s_1                       | shipmentLine_1                | 2021-04-20         | lotNumber     | huPackagingCode_2                       | bPartnerProductGTIN         |

    And the shipment identified by s_1 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

  Scenario: 1 Pack from 1 line with HU for entire qty
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

  M_ShipmentSchedule
  - QtyPickList = 10

    Given metasfresh contains M_Products:
      | Identifier | Name                          |
      | p_1        | salesProduct_14092022_1       |
      | p_2        | packingMaterial_LU_13092022_2 |
      | p_3        | packingMaterial_TU_13092022_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name_14092022_1 | pricing_system_value_14092022_1 | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_14092022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14092022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_1                             | p_3                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_14092022_1 | N            | Y              | ps_1                          |
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1            | true                     | bPartnerDesadvRecipientGLN |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1                | ISO1          | LU          |
      | huPackagingCode_2                | CART          | TU          |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                       |
      | huPackingLU           | huPackingLU_14092022_1     |
      | huPackingTU           | huPackingTU_14092022_1     |
      | huPackingVirtualPI    | No Packing Item_14092022_1 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                        | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID |
      | packingVersionLU              | huPackingLU           | packingVersionLU_14092022_1 | LU          | Y         | huPackagingCode_1         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_14092022_1 | TU          | Y         | huPackagingCode_2         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item_14092022_1  | V           | Y         |                           |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo     |
      | huProduct_inventory       | 2021-04-16T00:00:00Z | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory       | huProduct_inventoryLine       | p_1                     | 0       | 10       |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 10    | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU          | 1000017        | luLotNumber | S                  |
      | createdLU          | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1                             | endcustomer_1            | p_2                     | bPartnerProductGTIN_LU |
      | bp_2                             | endcustomer_1            | p_3                     | bPartnerProductGTIN_TU |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                   |
      | pm_1                               | p_2                         | packingMaterialTest_LU |
      | pm_2                               | p_3                         | packingMaterialTest_TU |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU              | createdLU          | huPiItemLU                 | 10  | pm_1                               | PM           |
      | huPiItemTU              | createdTU          | huPiItemTU                 | 10  | pm_2                               | PM           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.C_PaymentTerm_ID | deliveryRule |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_14092022_1 | 1000012              | F            |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU          | s_s_1                            | 10        | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdLU          | s_s_1                            |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | PD           | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | s_1                   | p_1                     | 10          | true      | ol_1                          |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_LU_ID.Identifier | OPT.GTIN_LU_PackingMaterial |
      | p_1                           | true                | createdLU              | huPackagingCode_1                       | bPartnerProductGTIN_LU      |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1                               | p_1                           | 10              | 10        | 10              | 10                  | 1         | s_1                       | shipmentLine_1                | 2021-04-20         | luLotNumber   | huPackagingCode_2                       | bPartnerProductGTIN_TU      |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18     |
      | p_1                           | ipaSSCC18_13092022 |

    And generate csv file for sscc labels for 'p_1'
      | ReportDataLine                                                                                                                                            |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                            |
      | %END%                                                                                                                                                     |
      | "1","ipaSSCC18_13092022","po_ref_14092022_1","16.04.2021","","salesProduct_14092022_1","1","0","210420","luLotNumber","","","","","","","","","","","","" |

    And the shipment identified by s_1 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

  Scenario: 2 Packs from 1 line with HU for partial qty & 1 packing item
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = created in the test

  M_ShipmentSchedule
  - QtyPickList = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                          |
      | p_1        | salesProduct_14092022_2       |
      | p_2        | packingMaterial_LU_14092022_2 |
      | p_3        | packingMaterial_TU_14092022_2 |
      | p_4        | packingMaterial_14092022_2    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_14092022_2 | pricing_system_value_14092022_2 | pricing_system_description_14092022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_14092022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14092022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_1                             | p_3                     | 10.0     | PCE               | Normal                        |
      | pp_4       | plv_1                             | p_4                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_14092022_2 | N            | Y              | ps_1                          |

    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  |
      | endcustomer_1            | true                     | bPartnerDesadvRecipientGLN |

    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1                | ISO1          | LU          |
      | huPackagingCode_2                | CART          | TU          |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                   |
      | pm_1                               | p_2                         | packingMaterialTest_LU |
      | pm_2                               | p_3                         | packingMaterialTest_TU |
      | pm_3                               | p_4                         | packingMaterialTest    |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                       |
      | huPackingLU           | huPackingLU_14092022_2     |
      | huPackingTU           | huPackingTU_14092022_2     |
      | huPackingVirtualPI    | No Packing Item_14092022_2 |
      | huPackingLU_2         | huPackingLU_14092022_3     |
      | huPackingTU_2         | huPackingTU_14092022_3     |
      | huPackingVirtualPI_2  | No Packing Item_14092022_3 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                        | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID |
      | packingVersionLU              | huPackingLU           | packingVersionLU_14092022_2 | LU          | Y         | huPackagingCode_1         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_14092022_2 | TU          | Y         | huPackagingCode_2         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item_14092022_2  | V           | Y         |                           |
      | packingVersionLU_2            | huPackingLU_2         | packingVersionLU_14092022_3 | LU          | Y         |                           |
      | packingVersionTU_2            | huPackingTU_2         | packingVersionTU_14092022_3 | TU          | Y         | huPackagingCode_2         |
      | packingVersionCU_2            | huPackingVirtualPI_2  | No Packing Item_14092022_3  | V           | Y         |                           |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |                                        |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |                                        |
      | huPiItemLU_2               | packingVersionLU_2            | 10  | HU       | huPackingTU_2                    |                                        |
      | huPiItemTU_2               | packingVersionTU_2            | 0   | PM       |                                  | pm_3                                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huProductTU                        | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |                                                  |                                      |
      | huAuditProductTU_2                 | huPiItemTU_2               | p_1                     | 10  | 2021-01-01 | huPackagingCode_1                                | gtinPiItemProduct                    |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI":
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
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo     |
      | huProduct_inventory       | 2021-04-16T00:00:00Z | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory       | huProduct_inventoryLine       | p_1                     | 0       | 5        |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 5     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier | M_Attribute_ID | Value       | AttributeValueType |
      | createdLU          | 1000017        | luLotNumber | S                  |
      | createdLU          | 540020         | 2021-04-20  | D                  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.GTIN               |
      | bp_1                             | endcustomer_1            | p_2                     | bPartnerProductGTIN_LU |
      | bp_2                             | endcustomer_1            | p_3                     | bPartnerProductGTIN_TU |
      | bp_3                             | endcustomer_1            | p_4                     | bPartnerProductGTIN    |

    And metasfresh contains M_HU_Item:
      | M_HU_Item_ID.Identifier | M_HU_ID.Identifier | M_HU_PI_Item_ID.Identifier | Qty | M_HU_PackingMaterial_ID.Identifier | OPT.ItemType |
      | huPiItemLU              | createdLU          | huPiItemLU                 | 10  | pm_1                               | PM           |
      | huPiItemTU              | createdTU          | huPiItemTU                 | 10  | pm_2                               | PM           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        | OPT.C_PaymentTerm_ID | deliveryRule |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock_14092022_2 | 1000012              | F            |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | huAuditProductTU_2                     | orderLineASI                             |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU          | s_s_1                            | 5         | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdLU          | s_s_1                            |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | PD           | true                | false       |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_LU_ID.Identifier | OPT.GTIN_LU_PackingMaterial |
      | p_1                           | true                | null                   | huPackagingCode_1                       | gtinPiItemProduct           |
      | p_2                           | true                | createdLU              | huPackagingCode_1                       | bPartnerProductGTIN_LU      |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU | OPT.BestBeforeDate | OPT.LotNumber | OPT.M_HU_PackagingCode_TU_ID.Identifier | OPT.GTIN_TU_PackingMaterial |
      | pi_1                               | p_1                           | 5               | 10        | 5               | 10                  | 1         | 2021-04-20         | lotNumber     | huPackagingCode_2                       | bPartnerProductGTIN         |
      | pi_2                               | p_2                           | 5               | 5         | 5               | 5                   | 1         | 2021-04-20         | luLotNumber   | huPackagingCode_2                       | bPartnerProductGTIN_TU      |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18       |
      | p_1                           | ipaSSCC18_14092022_1 |
      | p_2                           | ipaSSCC18_14092022_2 |

    And generate csv file for sscc labels for 'p_1,p_2'
      | ReportDataLine                                                                                                                                                   |
      | %BTW% /AF="\\\V-APSRV01\PRAGMA\ETIKETTEN\LAYOUTS\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\V-DCSRV02\ETIKETTEN01" /R=3 /P /D                                   |
      | %END%                                                                                                                                                            |
      | "1","ipaSSCC18_14092022_1","po_ref_mock_14092022_2","16.04.2021","","salesProduct_14092022_2","1","0","210420","lotNumber","","","","","","","","","","","",""   |
      | "1","ipaSSCC18_14092022_2","po_ref_mock_14092022_2","16.04.2021","","salesProduct_14092022_2","1","0","210420","luLotNumber","","","","","","","","","","","","" |
    
