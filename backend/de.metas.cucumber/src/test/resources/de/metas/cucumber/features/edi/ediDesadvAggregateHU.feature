@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
Feature: EDI DESADV with aggregate HU — QtyTU, QtyCUsPerTU, QtyCUsPerLU
## F00350: EDI
## Validates that aggregate HUs (one DB record representing N TUs) produce
## correct QtyTU, QtyCUsPerTU, and QtyCUsPerLU in DESADV pack items.
## Also tests INVOIC SU GLN COALESCE and GTIN population.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2026-03-11T13:30:13+01:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |


  @Id:S0500_010
  @from:cucumber
  Scenario: S0500_010 - Full delivery with aggregate HU: 2 TU x 2 PCE/TU = 4 PCE total
  Order 4 PCE of a product with TU capacity 2 PCE/TU. Shipment generation creates
  an aggregate HU (1 HU record representing 2 TUs with 4 PCE total).
  DESADV pack item must show QtyTU=2, QtyCUsPerTU=2, QtyCUsPerLU=4.

    Given metasfresh contains M_Products:
      | Identifier |
      | p_S0500    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_S0500   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0500   | ps_S0500           | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_S0500  | pl_S0500       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S0500              | p_S0500      | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S0500   | Y          | ps_S0500           | 9900000500010 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S0500      | true                 | 9900000500010         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S0500      | p_S0500      |

    # HU Packing Instruction hierarchy: LU holds up to 10 TU, each TU holds 2 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | pi_LU_S0500  |
      | pi_TU_S0500  |
      | pi_VHU_S0500 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID   | HU_UnitType | IsCurrent |
      | piv_LU_S0500       | pi_LU_S0500  | LU          | Y         |
      | piv_TU_S0500       | pi_TU_S0500  | TU          | Y         |
      | piv_VHU_S0500      | pi_VHU_S0500 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S0500    | piv_LU_S0500       | 10  | HU       | pi_TU_S0500       |
      | pii_TU_S0500    | piv_TU_S0500       | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pip_S0500               | pii_TU_S0500    | p_S0500      | 2   | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference        |
      | o_S0500    | true    | bp_S0500      | 2026-03-11  | po_ref_S0500_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S0500   | o_S0500    | p_S0500      | 4          | pip_S0500               |

    When the order identified by o_S0500 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_S0500   | ol_S0500       | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S0500              | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_S0500              | io_S0500   |

    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | movementqty | processed |
      | iol_S0500      | io_S0500   | p_S0500      | 4           | true      |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S0500         | true                |

    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID |
      | pi_S0500                | pack_S0500         | 2     | 2           | 4           | 4           | io_S0500   |


  @Id:S0500_020
  @from:cucumber
  Scenario: S0500_020 - Larger aggregate HU: 5 TU x 10 PCE/TU = 50 PCE total
  Order 50 PCE with TU capacity 10 PCE/TU. Shipment creates an aggregate HU
  representing 5 TUs. DESADV pack item: QtyTU=5, QtyCUsPerTU=10, QtyCUsPerLU=50.

    Given metasfresh contains M_Products:
      | Identifier  |
      | p_S0500_020 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0500_020 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0500_020 | ps_S0500_020       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0500_020 | pl_S0500_020   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S0500_020          | p_S0500_020  | 5.0      | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S0500_020  | Y          | ps_S0500_020       | 9900000500020 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S0500_020  | true                 | 9900000500020         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S0500_020  | p_S0500_020  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S0500_020  |
      | pi_TU_S0500_020  |
      | pi_VHU_S0500_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S0500_020    | pi_LU_S0500_020  | LU          | Y         |
      | piv_TU_S0500_020    | pi_TU_S0500_020  | TU          | Y         |
      | piv_VHU_S0500_020   | pi_VHU_S0500_020 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S0500_020  | piv_LU_S0500_020   | 20  | HU       | pi_TU_S0500_020   |
      | pii_TU_S0500_020  | piv_TU_S0500_020   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | pip_S0500_020           | pii_TU_S0500_020 | p_S0500_020  | 10  | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference            |
      | o_S0500_020  | true    | bp_S0500_020  | 2026-03-11  | po_ref_S0500_020_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S0500_020  | o_S0500_020 | p_S0500_020  | 50         | pip_S0500_020           |

    When the order identified by o_S0500_020 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S0500_020  | ol_S0500_020   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S0500_020          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_S0500_020          | io_S0500_020 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S0500_020     | true                |

    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID   |
      | pi_S0500_020            | pack_S0500_020     | 5     | 10          | 50          | 50          | io_S0500_020 |


  @Id:S0500_030
  @from:cucumber
  Scenario: S0500_030 - No packing item regression: 1 LU, 1 TU, all CUs in one TU
  Order 10 PCE without M_HU_PI_Item_Product. The non-HU path should create
  QtyTU=1, QtyCUsPerTU=10, QtyCUsPerLU=10.

    Given metasfresh contains M_Products:
      | Identifier  |
      | p_S0500_030 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0500_030 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0500_030 | ps_S0500_030       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0500_030 | pl_S0500_030   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S0500_030          | p_S0500_030  | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S0500_030 | Y          | ps_S0500_030       | 9900000500030 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S0500_030  | true                 | 9900000500030         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S0500_030  | p_S0500_030  |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference            |
      | o_S0500_030  | true    | bp_S0500_030  | 2026-03-11  | po_ref_S0500_030_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID | QtyEntered |
      | ol_S0500_030  | o_S0500_030 | p_S0500_030  | 10         |

    When the order identified by o_S0500_030 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S0500_030  | ol_S0500_030   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S0500_030          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_S0500_030          | io_S0500_030 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S0500_030     | true                |

    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID   |
      | pi_S0500_030            | pack_S0500_030     | 1     | 10          | 10          | 10          | io_S0500_030 |


  @Id:S0500_040
  @from:cucumber
  Scenario: S0500_040 - GTIN_CU from M_Product and GTIN_TU from PI Item Product in DESADV pack
  Product has GTIN set. M_HU_PI_Item_Product has GTIN_LU_PackingMaterial_Fallback.
  C_BPartner_Product has customer-specific GTIN.
  DESADV pack and pack item should reflect the GTINs.

    Given metasfresh contains M_Products:
      | Identifier  | GTIN          |
      | p_S0500_040 | 4001234567890 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0500_040 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0500_040 | ps_S0500_040       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0500_040 | pl_S0500_040   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S0500_040          | p_S0500_040  | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S0500_040 | Y          | ps_S0500_040       | 9900000500040 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S0500_040  | true                 | 9900000500040         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID | GTIN          |
      | bp_S0500_040  | p_S0500_040  | 0575095404663 |

    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID | PackagingCode | HU_UnitType |
      | hpc_LU_S0500_040      | ISO1          | LU          |
      | hpc_TU_S0500_040      | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S0500_040  |
      | pi_TU_S0500_040  |
      | pi_VHU_S0500_040 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID       | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | piv_LU_S0500_040    | pi_LU_S0500_040  | LU          | Y         |                       |
      | piv_TU_S0500_040    | pi_TU_S0500_040  | TU          | Y         | hpc_TU_S0500_040      |
      | piv_VHU_S0500_040   | pi_VHU_S0500_040 | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S0500_040  | piv_LU_S0500_040   | 10  | HU       | pi_TU_S0500_040   |
      | pii_TU_S0500_040  | piv_TU_S0500_040   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | pip_S0500_040           | pii_TU_S0500_040 | p_S0500_040  | 5   | 2020-01-01 | hpc_LU_S0500_040                  | 5412345000013                    |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference            |
      | o_S0500_040  | true    | bp_S0500_040  | 2026-03-11  | po_ref_S0500_040_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S0500_040  | o_S0500_040 | p_S0500_040  | 10         | pip_S0500_040           |

    When the order identified by o_S0500_040 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S0500_040  | ol_S0500_040   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S0500_040          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_S0500_040          | io_S0500_040 |

    # Verify DESADV pack has LU GTIN from PI Item Product fallback
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 | GTIN_PackingMaterial | M_HU_PackagingCode_ID |
      | pack_S0500_040     | true                | 5412345000013        | hpc_LU_S0500_040      |

    # Verify pack item has QtyTU=2, QtyCUsPerTU=5, and TU packaging code
    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID   | M_HU_PackagingCode_TU_ID |
      | pi_S0500_040            | pack_S0500_040     | 2     | 5           | 10          | 10          | io_S0500_040 | hpc_TU_S0500_040         |


  @Id:S0500_050
  @from:cucumber
  Scenario: S0500_050 - Partial delivery with aggregate HU: order 20 PCE, deliver 4 PCE = 2 TU
  Order 20 PCE with TU capacity 2 PCE/TU (= 10 TU full order).
  Override QtyToDeliver to 4 PCE. Shipment creates aggregate HU for 2 TU.
  DESADV pack item must show QtyTU=2, QtyCUsPerTU=2, QtyCUsPerLU=4.

    Given metasfresh contains M_Products:
      | Identifier  |
      | p_S0500_050 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0500_050 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0500_050 | ps_S0500_050       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0500_050 | pl_S0500_050   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S0500_050          | p_S0500_050  | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S0500_050 | Y          | ps_S0500_050       | 9900000500050 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S0500_050  | true                 | 9900000500050         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S0500_050  | p_S0500_050  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S0500_050  |
      | pi_TU_S0500_050  |
      | pi_VHU_S0500_050 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S0500_050    | pi_LU_S0500_050  | LU          | Y         |
      | piv_TU_S0500_050    | pi_TU_S0500_050  | TU          | Y         |
      | piv_VHU_S0500_050   | pi_VHU_S0500_050 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S0500_050  | piv_LU_S0500_050   | 20  | HU       | pi_TU_S0500_050   |
      | pii_TU_S0500_050  | piv_TU_S0500_050   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | pip_S0500_050           | pii_TU_S0500_050 | p_S0500_050  | 2   | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference            |
      | o_S0500_050  | true    | bp_S0500_050  | 2026-03-11  | po_ref_S0500_050_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S0500_050  | o_S0500_050 | p_S0500_050  | 20         | pip_S0500_050           |

    When the order identified by o_S0500_050 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S0500_050  | ol_S0500_050   | N             |

    # Override to deliver only 4 PCE (= 2 TU) of the 20 ordered
    And update shipment schedules
      | M_ShipmentSchedule_ID | QtyToDeliver_Override |
      | ss_S0500_050          | 4                     |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S0500_050          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_S0500_050          | io_S0500_050 |

    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID   | M_Product_ID | movementqty | processed |
      | iol_S0500_050  | io_S0500_050 | p_S0500_050  | 4           | true      |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S0500_050     | true                |

    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID   |
      | pi_S0500_050            | pack_S0500_050     | 2     | 2           | 4           | 4           | io_S0500_050 |
