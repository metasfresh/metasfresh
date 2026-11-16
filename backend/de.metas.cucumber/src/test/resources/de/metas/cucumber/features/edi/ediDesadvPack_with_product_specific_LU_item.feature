@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
Feature: EDI_DesadvPack, when the LU packing instruction has a product-specific LU->TU item
## F00350: EDI
  # A product-specific LU->TU item (M_HU_PI_Item.M_Product_ID) is used for that product only.
  # All other products keep using the generic item.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

  @Id:S32544_EDI_010
  Scenario: S32544_EDI_010 - product p_1 uses its product-specific LU item (10 TUs per LU): 1 pack with 40 CUs
  The LU packing instruction has a generic LU->TU item (2 TUs per LU) and an item restricted to product p_1 (10 TUs per LU).
  in:
  C_OrderLine:
  - QtyEntered = 4COLI = 40 CUs of p_1

    Given metasfresh contains M_Products:
      | Identifier  | OPT.X12DE355 |
      | p_1_010     | PCE          |
      | p_2_010     | PCE          |
      | p_3_010     | PCE          |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1_010   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_010   | ps_1_010           | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1_010  | pl_1_010       |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_010   | plv_1_010              | p_1_010      | 10.0     | PCE               | Normal                        |
      | pp_2_010   | plv_1_010              | p_2_010      | 10.0     | PCE               | Normal                        |
      | pp_3_010   | plv_1_010              | p_3_010      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | OPT.IsCustomer | M_PricingSystem_ID | GLN          |
      | endcustomer_010 | N      | Y              | ps_1_010           | location_gln |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID   | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | Identifier      |
      | endcustomer_010 | true                 | bPartnerDesadvRecipientGLN | edi_setting_010 |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier                  | M_Product_ID.Identifier | C_BPartner_ID.Identifier | SeqNo | GTIN          |
      | asi_p_2_010                 | p_2_010                 | endcustomer_010          | 10    | 0575095404663 |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name   |
      | pm_1_010                           | p_2_010                     | name_010 |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_010            | ISO1          | LU          |
      | huPackagingCode_2_010            | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | Identifier              |
      | huPackingLU_010         |
      | huPackingTU_010         |
      | huPackingVirtualPI_010  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier  | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID.Identifier |
      | packingVersionLU_010          | huPackingLU_010        | LU          | Y         |                                      |
      | packingVersionTU_010          | huPackingTU_010        | TU          | Y         | huPackagingCode_2_010                |
      | packingVersionCU_010          | huPackingVirtualPI_010 | V           | Y         |                                      |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier |
      | huPiItemLU_generic_010     | packingVersionLU_010          | 2   | HU       | huPackingTU_010                  |                                        |                             |
      | huPiItemLU_p1_010          | packingVersionLU_010          | 10  | HU       | huPackingTU_010                  |                                        | p_1_010                     |
      | huPiItemTU_010             | packingVersionTU_010          | 0   | PM       |                                  | pm_1_010                               |                             |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huProductTU_p1_010                 | huPiItemTU_010             | p_1_010                 | 10  | 2021-01-01 | huPackagingCode_1_010                            | gtinPiItemProduct                    |
      | huProductTU_p3_010                 | huPiItemTU_010             | p_3_010                 | 10  | 2021-01-01 | huPackagingCode_1_010                            | gtinPiItemProduct                    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1_010    | true    | endcustomer_010          | 2021-04-17  | po_ref_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1_010   | o_1_010               | p_1_010              | 4          | huProductTU_p1_010                         | COLI                  | 10                  |

    When the order identified by o_1_010 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_010  | ol_1_010                  | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_010                        | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_010                        | s_1_010               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_010        | s_1_010               | p_1_010              | 40          | true      | ol_1_010                      |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | p_1_010 | true | null | huPackagingCode_1_010 | gtinPiItemProduct | 1 |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerTU | QtyCUsPerLU | QtyItemCapacity | QtyTU | M_InOut_ID.Identifier | M_InOutLine_ID     | M_HU_PackagingCode_TU_ID | GTIN_TU_PackingMaterial |
      | pi_1_010 | p_1_010 | 40 | 10 | 40 | 10 | 4 | s_1_010 | shipmentLine_1_010 | huPackagingCode_2_010 | 0575095404663 |

    And EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IPA_SSCC18       |
      | p_1_010                       | ipaSSCC18_32544_1    |
    # the label quantity is the number of TUs on the LU (4 with the product-specific item; the generic item would split them over 2 LUs)
    And generate csv file for sscc labels for 'p_1_010'
      | ReportDataLine |
      | %BTW% /AF="\\\\V-APSRV01\\PRAGMA\\ETIKETTEN\\LAYOUTS\\SSCC.BTW" /D="<TRIGGER FILE NAME>" /PRN="\\\\V-DCSRV02\\ETIKETTEN01" /R=3 /P /D |
      | %END% |
      | "1","ipaSSCC18_32544_1","@o_1_010.orderPOReference@","16.04.2021","","@p_1_010.productName@","4","0","","","location_gln","","","","","","location_gln","","","","","" |

    And the shipment identified by s_1_010 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack

  @Id:S32544_EDI_020
  Scenario: S32544_EDI_020 - product p_3 has no product-specific item and keeps the generic LU item (2 TUs per LU): 2 packs with 20 CUs each
  The LU packing instruction has a generic LU->TU item (2 TUs per LU) and an item restricted to product p_1 (10 TUs per LU).
  in:
  C_OrderLine:
  - QtyEntered = 4COLI = 40 CUs of p_3

    Given metasfresh contains M_Products:
      | Identifier  | OPT.X12DE355 |
      | p_1_020     | PCE          |
      | p_2_020     | PCE          |
      | p_3_020     | PCE          |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1_020   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1_020   | ps_1_020           | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1_020  | pl_1_020       |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_020   | plv_1_020              | p_1_020      | 10.0     | PCE               | Normal                        |
      | pp_2_020   | plv_1_020              | p_2_020      | 10.0     | PCE               | Normal                        |
      | pp_3_020   | plv_1_020              | p_3_020      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | OPT.IsCustomer | M_PricingSystem_ID | GLN          |
      | endcustomer_020 | N      | Y              | ps_1_020           | location_gln |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID   | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | Identifier      |
      | endcustomer_020 | true                 | bPartnerDesadvRecipientGLN | edi_setting_020 |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier                  | M_Product_ID.Identifier | C_BPartner_ID.Identifier | SeqNo | GTIN          |
      | asi_p_2_020                 | p_2_020                 | endcustomer_020          | 10    | 0575095404663 |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name   |
      | pm_1_020                           | p_2_020                     | name_020 |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID.Identifier | PackagingCode | HU_UnitType |
      | huPackagingCode_1_020            | ISO1          | LU          |
      | huPackagingCode_2_020            | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | Identifier              |
      | huPackingLU_020         |
      | huPackingTU_020         |
      | huPackingVirtualPI_020  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier  | HU_UnitType | IsCurrent | OPT.M_HU_PackagingCode_ID.Identifier |
      | packingVersionLU_020          | huPackingLU_020        | LU          | Y         |                                      |
      | packingVersionTU_020          | huPackingTU_020        | TU          | Y         | huPackagingCode_2_020                |
      | packingVersionCU_020          | huPackingVirtualPI_020 | V           | Y         |                                      |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier |
      | huPiItemLU_generic_020     | packingVersionLU_020          | 2   | HU       | huPackingTU_020                  |                                        |                             |
      | huPiItemLU_p1_020          | packingVersionLU_020          | 10  | HU       | huPackingTU_020                  |                                        | p_1_020                     |
      | huPiItemTU_020             | packingVersionTU_020          | 0   | PM       |                                  | pm_1_020                               |                             |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PackagingCode_LU_Fallback_ID.Identifier | OPT.GTIN_LU_PackingMaterial_Fallback |
      | huProductTU_p1_020                 | huPiItemTU_020             | p_1_020                 | 10  | 2021-01-01 | huPackagingCode_1_020                            | gtinPiItemProduct                    |
      | huProductTU_p3_020                 | huPiItemTU_020             | p_3_020                 | 10  | 2021-01-01 | huPackagingCode_1_020                            | gtinPiItemProduct                    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1_020    | true    | endcustomer_020          | 2021-04-17  | po_ref_@Date@   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1_020   | o_1_020               | p_3_020              | 4          | huProductTU_p3_020                         | COLI                  | 10                  |

    When the order identified by o_1_020 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_020  | ol_1_020                  | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_020                        | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_020                        | s_1_020               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1_020        | s_1_020               | p_3_020              | 40          | true      | ol_1_020                      |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | p_1_020 | true | null | huPackagingCode_1_020 | gtinPiItemProduct | 1 |
      | p_2_020 | true | null | huPackagingCode_1_020 | gtinPiItemProduct | 2 |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerTU | QtyCUsPerLU | QtyItemCapacity | QtyTU | M_InOut_ID.Identifier | M_InOutLine_ID     | M_HU_PackagingCode_TU_ID | GTIN_TU_PackingMaterial |
      | pi_1_020 | p_1_020 | 20 | 10 | 20 | 10 | 2 | s_1_020 | shipmentLine_1_020 | huPackagingCode_2_020 | 0575095404663 |
      | pi_2_020 | p_2_020 | 20 | 10 | 20 | 10 | 2 | s_1_020 | shipmentLine_1_020 | huPackagingCode_2_020 | 0575095404663 |

    And the shipment identified by s_1_020 is reversed

    Then after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack
