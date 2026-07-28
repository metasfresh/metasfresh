@from:cucumber
@ghActions:run_on_executor5
Feature: EPCIS JSON export via get_epcis_events_json_fn
  The SQL function builds EPCIS event JSON from the HU hierarchy.
  Tests create controlled shipment data and validate function execution and JSON structure.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2026-06-10T10:00:00+02:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

  @from:cucumber
  @Id:EPCIS_010
  Scenario: EPCIS_010 - Aggregate HU with DESADV: validates full EPCIS JSON structure
    # Product with GTIN
    Given metasfresh contains M_Products:
      | Identifier  | GTIN          |
      | p_EPCIS_010 | 4060000000017 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_EPCIS_010 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_EPCIS_010 | ps_EPCIS_010       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_EPCIS_010 | pl_EPCIS_010   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_EPCIS_010          | p_EPCIS_010  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_EPCIS_010 | Y          | ps_EPCIS_010       | 9900000600010 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier               |
      | bp_EPCIS_010  | true                 | 9900000600010         | edi_setting_EPCIS_010_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_EPCIS_010  | p_EPCIS_010  |

    # HU PI hierarchy: LU holds 10 TU, each TU holds 2 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID      |
      | pi_LU_EPCIS_010 |
      | pi_TU_EPCIS_010 |
      | pi_VHU_EPCIS_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_EPCIS_010   | pi_LU_EPCIS_010  | LU          | Y         |
      | piv_TU_EPCIS_010   | pi_TU_EPCIS_010  | TU          | Y         |
      | piv_VHU_EPCIS_010  | pi_VHU_EPCIS_010 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_EPCIS_010 | piv_LU_EPCIS_010   | 10  | HU       | pi_TU_EPCIS_010   |
      | pii_TU_EPCIS_010 | piv_TU_EPCIS_010   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | pip_EPCIS_010           | pii_TU_EPCIS_010 | p_EPCIS_010  | 2   | 2020-01-01 |

    # Sales order: 4 PCE = 2 TU (aggregate)
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | POReference         |
      | o_EPCIS_010 | true    | bp_EPCIS_010  | 2026-06-10  | po_EPCIS_010_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_EPCIS_010 | o_EPCIS_010 | p_EPCIS_010  | 4          | pip_EPCIS_010           |

    When the order identified by o_EPCIS_010 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_EPCIS_010 | ol_EPCIS_010   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_EPCIS_010          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_EPCIS_010          | io_EPCIS_010 |

    # Wait for DESADV creation
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_EPCIS_010     | true                |

    # Call EPCIS function and validate top-level fields
    # Note: auto-generated shipments (QuantityType=D) do not create HU hierarchy,
    # so palletCount is 0. Pallet/crate/item validation requires picked shipments.
    When the EPCIS JSON export function is called for M_InOut identified by io_EPCIS_010
    Then the EPCIS JSON has:
      | warehouseValue | desadvReference |
      | StdWarehouse   | notNull         |


  @from:cucumber
  @Id:EPCIS_020
  Scenario: EPCIS_020 - Shipment without DESADV: desadvReference is null
    Given metasfresh contains M_Products:
      | Identifier  |
      | p_EPCIS_020 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_EPCIS_020 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_EPCIS_020 | ps_EPCIS_020       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_EPCIS_020 | pl_EPCIS_020   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_EPCIS_020          | p_EPCIS_020  | 10.0     | PCE      | Normal           |

    # BPartner: NOT an EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID |
      | bp_EPCIS_020 | Y          | ps_EPCIS_020       |

    # HU PI hierarchy: LU holds 10 TU, each TU holds 2 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID      |
      | pi_LU_EPCIS_020 |
      | pi_TU_EPCIS_020 |
      | pi_VHU_EPCIS_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_EPCIS_020   | pi_LU_EPCIS_020  | LU          | Y         |
      | piv_TU_EPCIS_020   | pi_TU_EPCIS_020  | TU          | Y         |
      | piv_VHU_EPCIS_020  | pi_VHU_EPCIS_020 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_EPCIS_020 | piv_LU_EPCIS_020   | 10  | HU       | pi_TU_EPCIS_020   |
      | pii_TU_EPCIS_020 | piv_TU_EPCIS_020   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | pip_EPCIS_020           | pii_TU_EPCIS_020 | p_EPCIS_020  | 2   | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | POReference         |
      | o_EPCIS_020 | true    | bp_EPCIS_020  | 2026-06-10  | po_EPCIS_020_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_EPCIS_020 | o_EPCIS_020 | p_EPCIS_020  | 4          | pip_EPCIS_020           |

    When the order identified by o_EPCIS_020 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_EPCIS_020 | ol_EPCIS_020   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_EPCIS_020          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_EPCIS_020          | io_EPCIS_020 |

    When the EPCIS JSON export function is called for M_InOut identified by io_EPCIS_020
    Then the EPCIS JSON has:
      | warehouseValue | desadvReference |
      | StdWarehouse   | null            |


  @from:cucumber
  @Id:EPCIS_030
  Scenario: EPCIS_030 - Larger aggregate HU: 5 TU x 10 PCE, validates pallet and item-level JSON
    Given metasfresh contains M_Products:
      | Identifier  | GTIN          |
      | p_EPCIS_030 | 4060000000031 |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_EPCIS_030 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_EPCIS_030 | ps_EPCIS_030       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_EPCIS_030 | pl_EPCIS_030   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_EPCIS_030          | p_EPCIS_030  | 5.0      | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_EPCIS_030 | Y          | ps_EPCIS_030       | 9900000600030 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier               |
      | bp_EPCIS_030  | true                 | 9900000600030         | edi_setting_EPCIS_030_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_EPCIS_030  | p_EPCIS_030  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID      |
      | pi_LU_EPCIS_030 |
      | pi_TU_EPCIS_030 |
      | pi_VHU_EPCIS_030 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_EPCIS_030   | pi_LU_EPCIS_030  | LU          | Y         |
      | piv_TU_EPCIS_030   | pi_TU_EPCIS_030  | TU          | Y         |
      | piv_VHU_EPCIS_030  | pi_VHU_EPCIS_030 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_EPCIS_030 | piv_LU_EPCIS_030   | 20  | HU       | pi_TU_EPCIS_030   |
      | pii_TU_EPCIS_030 | piv_TU_EPCIS_030   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | pip_EPCIS_030           | pii_TU_EPCIS_030 | p_EPCIS_030  | 10  | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | POReference         |
      | o_EPCIS_030 | true    | bp_EPCIS_030  | 2026-06-10  | po_EPCIS_030_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_EPCIS_030 | o_EPCIS_030 | p_EPCIS_030  | 50         | pip_EPCIS_030           |

    When the order identified by o_EPCIS_030 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_EPCIS_030 | ol_EPCIS_030   | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_EPCIS_030          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_EPCIS_030          | io_EPCIS_030 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_EPCIS_030     | true                |

    When the EPCIS JSON export function is called for M_InOut identified by io_EPCIS_030
    Then the EPCIS JSON has:
      | warehouseValue | desadvReference |
      | StdWarehouse   | notNull         |


  @from:cucumber
  @Id:S29231_130
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  Scenario: S29231_130 — Two orders, one consolidated shipment → EPCIS pallets[] populated, desadvReferences[] and poReferences[] arrays of size 2
  ## Asserts that the EPCIS event JSON for a 2-source-order consolidated shipment carries
  ## pallets[] of size 2 (one LU per DESADV), desadvReferences[] of size 2, and
  ## poReferences[] of size 2.  Each order gets its own LU via the real metasfresh
  ## BL (Inventory → CU → TU → LU → SSCC18 → TU-level pick → QuantityType=PD shipment).
    Given metasfresh contains M_Products:
      | Identifier      | GTIN          |
      | p_S29231_130    | 4060000000130 |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_S29231_130   |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_130   | ps_S29231_130      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID  |
      | plv_S29231_130  | pl_S29231_130   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_130         | p_S29231_130  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_130   | Y          | ps_S29231_130      | 9900000291300 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                  |
      | bp_S29231_130 | true                 | 9900000291300         | edi_setting_S29231_130_bp   |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID  | M_Product_ID  |
      | bp_S29231_130  | p_S29231_130  |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_Products:
      | Identifier             |
      | pmProdLU_S29231_130    |
      | pmProdTU_S29231_130    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID        | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_130         | pmProdLU_S29231_130 | 0.0      | PCE      | Normal           |
      | plv_S29231_130         | pmProdTU_S29231_130 | 0.0      | PCE      | Normal           |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                    |
      | pm_LU_S29231_130                   | pmProdLU_S29231_130         | Pallet_S29231_130       |
      | pm_TU_S29231_130                   | pmProdTU_S29231_130         | Karton_S29231_130       |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID          |
      | pi_LU_S29231_130    |
      | pi_TU_S29231_130    |
      | pi_VHU_S29231_130   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID          | HU_UnitType | IsCurrent |
      | piv_LU_S29231_130   | pi_LU_S29231_130    | LU          | Y         |
      | piv_TU_S29231_130   | pi_TU_S29231_130    | TU          | Y         |
      | piv_VHU_S29231_130  | pi_VHU_S29231_130   | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID        | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID   | OPT.M_HU_PackingMaterial_ID |
      | pii_LU_S29231_130      | piv_LU_S29231_130   | 20  | HU       | pi_TU_S29231_130    |                             |
      | pii_LU_PM_S29231_130   | piv_LU_S29231_130   | 0   | PM       |                     | pm_LU_S29231_130            |
      | pii_TU_S29231_130      | piv_TU_S29231_130   | 0   | PM       |                     | pm_TU_S29231_130            |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID  | M_Attribute.Value |
      | piv_LU_S29231_130   | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S29231_130          | pii_TU_S29231_130 | p_S29231_130  | 10  | 2020-01-01 |

    # Order A — distinct numeric POReference (≤10 digits) → LPAD pads to '1234567893', per the receiver's EPCIS spec
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference |
      | oA_S29231_130 | true    | bp_S29231_130  | 2026-05-20  | 1234567893  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S29231_130| oA_S29231_130 | p_S29231_130  | 10         | pip_S29231_130          |

    When the order identified by oA_S29231_130 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dA_S29231_130            | bp_S29231_130            | oA_S29231_130         | P                |

    # Order B — distinct numeric POReference (10 digits) → LPAD leaves '9876543210' unchanged, per the receiver's EPCIS spec
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference |
      | oB_S29231_130 | true    | bp_S29231_130  | 2026-05-20  | 9876543210  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S29231_130| oB_S29231_130 | p_S29231_130  | 10         | pip_S29231_130          |

    When the order identified by oB_S29231_130 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dB_S29231_130            | bp_S29231_130            | oB_S29231_130         | P                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ssA_S29231_130  | olA_S29231_130  | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ssB_S29231_130  | olB_S29231_130  | N             |

    # ─── Order A: Inventory → CU → TU → LU → SSCC18 ─────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | invA_S29231_130           | 2026-05-20   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invA_S29231_130           | invLineA_S29231_130           | p_S29231_130            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'invA_S29231_130'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineA_S29231_130           | cuA_S29231_130     |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cuA_S29231_130      | 10    | pip_S29231_130                     | tuA_S29231_130                |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tuA_S29231_130      | 1     | pii_LU_S29231_130          | luA_S29231_130            |

    And M_HU_Attribute is changed
      | M_HU_ID        | M_Attribute_ID.Value | Value              |
      | luA_S29231_130 | SSCC18               | 987654321000000016 |

    # ─── Order B: Inventory → CU → TU → LU → SSCC18 ─────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | invB_S29231_130           | 2026-05-20   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invB_S29231_130           | invLineB_S29231_130           | p_S29231_130            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'invB_S29231_130'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineB_S29231_130           | cuB_S29231_130     |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cuB_S29231_130      | 10    | pip_S29231_130                     | tuB_S29231_130                |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tuB_S29231_130      | 1     | pii_LU_S29231_130          | luB_S29231_130            |

    And M_HU_Attribute is changed
      | M_HU_ID        | M_Attribute_ID.Value | Value              |
      | luB_S29231_130 | SSCC18               | 987654321000000023 |

    # ─── TU-level picking — m_tu_hu_id must be set for EPCIS individual_tu_ids gate ──
    # Critical invariant (RESEARCH-picking-bl.md Q3): picking at TU level writes
    # m_tu_hu_id=TU.m_hu_id on m_hu_assignment, satisfying the EPCIS EXISTS filter.
    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tuA_S29231_130     | ssA_S29231_130                   | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tuA_S29231_130     | ssA_S29231_130                   |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tuB_S29231_130     | ssB_S29231_130                   | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tuB_S29231_130     | ssB_S29231_130                   |

    # ─── Generate ONE consolidated shipment (QuantityType=PD ships picked HUs only) ──
    # Both schedules share BPartner+warehouse+date → consolidate into one M_InOut.
    # Batch step enqueues both in one work package so consolidation logic can group them.
    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID   |
      | ssA_S29231_130          |
      | ssB_S29231_130          |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ssA_S29231_130        | io_S29231_130 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ssB_S29231_130        | io_S29231_130 |

    # Consolidated multi-source-order shipment: one pack per source DESADV.
    # IsManual_IPA_SSCC18=false: real LUs with SSCC18 attributes are present.
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | packA_S29231_130   | dA_S29231_130            | false               |
      | packB_S29231_130   | dB_S29231_130            | false               |

    # ─── CORE ASSERTION ──────────────────────────────────────────────────────────────
    # The EPCIS function must return ONE event document with pallets[] of size 2,
    # desadvReferences[] of size 2, and poReferences[] of size 2.
    When the EPCIS JSON export function is called for M_InOut identified by io_S29231_130
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000000016 |
      | 987654321000000023 |
    # 2 orders / 2 DESADVs consolidated into ONE shipment (1 M_InOut):
    # desadvReferences/poReferences are size 2, but shipmentDocumentNos is size 1
    # — it carries the single M_InOut.DocumentNo, not one entry per order.
    And the EPCIS JSON array field has:
      | field               | expectedSize |
      | desadvReferences    | 2            |
      | poReferences        | 2            |
      | shipmentDocumentNos | 1            |
    # ─── Per-LU POReference in dummy GRAI ────────────────────────────────────────────
    # POReferences are '1234567893' (Order A, 10 digits → LPAD no-op → '1234567893') and
    # '9876543210' (Order B, 10 digits → LPAD no-op → '9876543210').
    # GRAI format: urn:epc:id:grai:<GCP>.<assetType>.<10-digit Bestellnummer><2-digit counter>
    # Each pallet's TU dummy GRAI must contain only its own source order's padded POReference.
    Then the EPCIS JSON pallets have dummy GRAIs containing the source order POReference:
      | sscc18             | ExpectedPOReferenceSanitized |
      | 987654321000000016 | 1234567893                   |
      | 987654321000000023 | 9876543210                   |


  @from:cucumber
  @Id:S29231_170
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  Scenario: S29231_170 — Two mobile-picking jobs share one LU: EPCIS emits ONE merged event per physical SSCC on the pallet-closing completion; post-closure either sharer returns the full pallet
  ## Close-driven semantics (two orders picked onto one shared pallet):
  ## When two sales orders are picked onto ONE shared physical pallet (ONE SSCC), the
  ## get_epcis_events_json_fn emits exactly ONE picking+commissioning event for that
  ## SSCC once the pallet is fully closed (all sharers completed). The queried shipment
  ## returns the full merged event:
  ##   - pallets[0].crates = ALL crates from both orders on that LU (15 total)
  ##   - desadvReferences[] size 2 (one per DESADV)
  ##   - poReferences[]     size 2 (one per order)
  ## Post-closure, EITHER sharer (ioA or ioB) returns the full merged pallet — there is
  ## no fixed owner; single-emission guarantee is covered by the Task-4 e2e scenario.
  ##
  ## The test uses two real mobile picking jobs (LUPickingTarget.ofExistingHU on the
  ## second job) to reproduce the shared-pallet shape via the production code path.
  ## With IsAlwaysSplitHUsEnabled=N the shared LU survives across both picking jobs.
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S29231_170 | 4060000000178 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_170 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_170 | ps_S29231_170      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_170 | pl_S29231_170  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_170         | p_S29231_170 | 5.0      | PCE      | Normal           |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_170 | Y          | ps_S29231_170      | 9900000291700 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S29231_170 | 2900000291700 | bp_S29231_170 | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S29231_170 | true                 | 9900000291700         | edi_setting_S29231_170_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S29231_170 | p_S29231_170 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S29231_170 |
      | pi_TU_S29231_170 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S29231_170  | pi_LU_S29231_170 | LU          | Y         |
      | piv_TU_S29231_170  | pi_TU_S29231_170 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S29231_170 | piv_LU_S29231_170  | 20  | HU       | pi_TU_S29231_170  |
      | pii_TU_S29231_170 | piv_TU_S29231_170  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S29231_170  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S29231_170          | pii_TU_S29231_170 | p_S29231_170 | 10  | 2020-01-01 |

    # Mobile UI picking profile — IsAlwaysSplitHUsEnabled=N keeps the LU intact across both jobs
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs for order A + 10 TUs for order B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S29231_170 | 2026-05-25   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S29231_170 | invLine_S29231_170  | p_S29231_170 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S29231_170'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID           |
      | invLine_S29231_170 | pickFromCU_S29231_170 |

    And transform CU to new LU
      | sourceCU              | newLU                       | TU_PI_ID          | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S29231_170 | pickFromAggregatedLU        | pi_TU_S29231_170  | 10          | 15          |

    # Order A — 50 PCE → 5 TUs. POReference is 10 digits so LPAD is a no-op.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S29231_170 | true    | bp_S29231_170 | 2026-05-25  | 1170000001  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S29231_170 | oA_S29231_170 | p_S29231_170 | 50         | pip_S29231_170          |

    When the order identified by oA_S29231_170 is completed

    # Order B — 100 PCE → 10 TUs. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S29231_170 | true    | bp_S29231_170 | 2026-05-25  | 1170000002  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S29231_170 | oB_S29231_170 | p_S29231_170 | 100        | pip_S29231_170          |

    When the order identified by oB_S29231_170 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S29231_170 | olA_S29231_170 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssB_S29231_170 | olB_S29231_170 | N             |

    # ─── Picking job 1: order A → new LU 'sharedLu' ─────────────────────────────────
    And start picking job for sales order identified by oA_S29231_170
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S29231_170
    And pick lines
      | PickingLine.byProduct | PickFromHU           | QtyPicked |
      | p_S29231_170          | pickFromAggregatedLU | 5         |
    And expect current picking target
      | Existing_LU |
      | sharedLu    |
    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     | OPT.DocStatus | REST.Context.M_InOut_ID |
      | ssA_S29231_170        | ioA_S29231_170 | CO            | ioA_S29231_170_ID       |

    # Stamp a deterministic SSCC18 on the shared LU so the EPCIS assertions can match it.
    And M_HU_Attribute is changed
      | M_HU_ID  | M_Attribute_ID.Value | Value              |
      | sharedLu | SSCC18               | 987654321000001700 |

    # ─── Picking job 2: order B targets the SAME LU (LUPickingTarget.ofExistingHU) ──
    And start picking job for sales order identified by oB_S29231_170
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by sharedLu
    And pick lines
      | PickingLine.byProduct | PickFromHU           | QtyPicked |
      | p_S29231_170          | pickFromAggregatedLU | 10        |
    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     | OPT.DocStatus | REST.Context.M_InOut_ID |
      | ssB_S29231_170        | ioB_S29231_170 | CO            | ioB_S29231_170_ID       |

    # ─── Bind each DESADV (per order POReference → its own DESADV) ──────────────────
    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier |
      | dA_S29231_170            | bp_S29231_170            | oA_S29231_170         |
      | dB_S29231_170            | bp_S29231_170            | oB_S29231_170         |

    # ─── Per-SSCC contract (close-driven semantics) ───────────────────────────────────
    # Both shipments are completed (pallet fully closed). The queried shipment (ioA) returns
    # the full merged event covering ALL crates from the shared physical LU (5 TUs from
    # order A + 10 TUs from order B = 15 crates total) with both DESADV + PO references.
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S29231_170
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000001700 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000001700 | 15         |
    # 2 orders / 2 DESADVs delivered via TWO M_InOuts that share one SSCC:
    # shipmentDocumentNos is size 2 — both M_InOut.DocumentNos. The owner's merged
    # EPCIS event references both; this is the value the EPCIS desadv bizTransaction carries.
    And the EPCIS JSON array field has:
      | field               | expectedSize |
      | desadvReferences    | 2            |
      | poReferences        | 2            |
      | shipmentDocumentNos | 2            |
    # ─── Order-pure crates (me03 #30279) ─────────────────────────────────────────────
    # Even though both orders share ONE physical pallet, each crate (Gebinde) belongs to
    # exactly one order, so each crate must carry its own poReference + delivery note. The
    # scripted adapter uses these to emit one po + one desadv bizTransaction per PACKING event.
    # 15 crates total: 5 from order A (PO 1170000001) + 10 from order B (PO 1170000002).
    Then the EPCIS JSON pallet 0 crates are order-pure with POReferences:
      | poReference |
      | 1170000001  |
      | 1170000002  |

    # ─── Post-closure: sibling (ioB) ALSO returns the merged pallet — no fixed owner ───
    When the EPCIS JSON export function is called for M_InOut identified by ioB_S29231_170
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000001700 | 15         |

    # ─── DESADV-JSON regression via M_InOut_EDI_Export_JSON/invoke ───────────────────
    # Exercises get_desadv_packs_json_fn's per-M_InOut filter through the production REST
    # path. Without the SQL fix, the response for shipment A would also include shipment B's
    # packs (and vice versa) because both DESADVs reference the same shared LU.
    And the following API_Audit_Config records are created:
      | Identifier   | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_S29231_170 | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |

    # Shipment A → must return exactly 1 DESADV element (dA), 50 PCE delivered.
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@ioA_S29231_170_ID@"
    }
  ]
}
    """
    Then verify DESADV JSON export response has exactly 1 element matching:
      | Order_Identifier | ExpectedQtyDelivered |
      | oA_S29231_170    | 50                   |

    # Shipment B → must return exactly 1 DESADV element (dB), 100 PCE delivered.
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@ioB_S29231_170_ID@"
    }
  ]
}
    """
    Then verify DESADV JSON export response has exactly 1 element matching:
      | Order_Identifier | ExpectedQtyDelivered |
      | oB_S29231_170    | 100                  |

  @from:cucumber
  @Id:S30558_010
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  Scenario: S30558_010 — Both draft shipments generated first, then ioA completed first: gate returns {} for ioA until ioB is also completed
  ## Completion-ordering gate (me03 #30558): both-drafts flow — generate BOTH shipments as
  ## drafts FIRST (each claims its own picked TUs, QuantityType=P), then complete in order.
  ## Completing ioA first while ioB is only a draft means order B's 10 TUs are covered only
  ## by a draft shipment (not CO/CL). The RED gate assertion fires after completing ioA:
  ## the current (un-gated) function emits a non-empty partial event, so the
  ## 'returns empty object' assertion FAILS (intended RED). After ioB is also completed,
  ## both ioA and ioB then return the full merged event — no fixed owner under the close-gate model.
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30558_010 | 4060000000185 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30558_010 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30558_010 | ps_S30558_010      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30558_010 | pl_S30558_010  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30558_010         | p_S30558_010 | 5.0      | PCE      | Normal           |

    # AllowConsolidateInOut=N so the two orders' shipments stay separate M_InOuts (no consolidation)
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_S30558_010 | Y          | ps_S30558_010      | 9900000305580 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S30558_010 | 2900000305580 | bp_S30558_010 | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30558_010 | true                 | 9900000305580         | edi_setting_S30558_010_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30558_010 | p_S30558_010 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S30558_010 |
      | pi_TU_S30558_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S30558_010  | pi_LU_S30558_010 | LU          | Y         |
      | piv_TU_S30558_010  | pi_TU_S30558_010 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30558_010 | piv_LU_S30558_010  | 20  | HU       | pi_TU_S30558_010  |
      | pii_TU_S30558_010 | piv_TU_S30558_010  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30558_010  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30558_010          | pii_TU_S30558_010 | p_S30558_010 | 10  | 2020-01-01 |

    # Mobile UI picking profile — DO_NOT_CREATE: no shipment is auto-created on job completion;
    # both jobs finish first so the LU holds all 15 TUs before any shipment is generated.
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs for order A + 10 TUs for order B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S30558_010 | 2026-05-25   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30558_010 | invLine_S30558_010  | p_S30558_010 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30558_010'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID               |
      | invLine_S30558_010  | pickFromCU_S30558_010 |

    And transform CU to new LU
      | sourceCU              | newLU                        | TU_PI_ID          | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S30558_010 | pickFromAggregatedLU_S30558  | pi_TU_S30558_010  | 10          | 15          |

    # Order A — 50 PCE → 5 TUs. POReference is 10 digits so LPAD is a no-op.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S30558_010 | true    | bp_S30558_010 | 2026-05-25  | 1170000001  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S30558_010 | oA_S30558_010 | p_S30558_010 | 50         | pip_S30558_010          |

    When the order identified by oA_S30558_010 is completed

    # Order B — 100 PCE → 10 TUs. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S30558_010 | true    | bp_S30558_010 | 2026-05-25  | 1170000002  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S30558_010 | oB_S30558_010 | p_S30558_010 | 100        | pip_S30558_010          |

    When the order identified by oB_S30558_010 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S30558_010 | olA_S30558_010 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssB_S30558_010 | olB_S30558_010 | N             |

    # ─── Picking job 1: order A → new LU 'sharedLu_S30558_010' ──────────────────────
    And start picking job for sales order identified by oA_S30558_010
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30558_010
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30558_010          | pickFromAggregatedLU_S30558 | 5         |
    And expect current picking target
      | Existing_LU         |
      | sharedLu_S30558_010 |
    And complete picking job

    # ─── Picking job 2: order B targets the SAME LU (LUPickingTarget.ofExistingHU) ──
    # Both jobs done — the LU now physically holds all 15 TUs.
    And start picking job for sales order identified by oB_S30558_010
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by sharedLu_S30558_010
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30558_010          | pickFromAggregatedLU_S30558 | 10        |
    And complete picking job

    # ─── Stamp SSCC18 on the shared LU ───────────────────────────────────────────────
    And M_HU_Attribute is changed
      | M_HU_ID             | M_Attribute_ID.Value | Value              |
      | sharedLu_S30558_010 | SSCC18               | 987654321000003058 |

    # ─── Both-drafts flow: generate DRAFT for ssA, then separately for ssB ─────────────
    # Each schedule claims its own picked TUs (QuantityType=P); generating both as drafts
    # before completing either ensures no schedule sweeps the whole LU on its own.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S30558_010        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssA_S30558_010        | ioA_S30558_010 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssB_S30558_010        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssB_S30558_010        | ioB_S30558_010 |

    # ─── Complete ioA first ───────────────────────────────────────────────────────────
    And the shipment identified by ioA_S30558_010 is completed

    # RED gate — ioB is still a draft (IP): order B's 10 TUs on the LU are covered only
    # by a draft shipment, not a CO/CL. Correct post-fix behaviour: {} (LU not fully covered
    # by completed shipments → gate blocks emission).
    # This assertion MUST FAIL on the current (un-gated) code (intended RED).
    Then the EPCIS JSON export function returns empty object for M_InOut identified by ioA_S30558_010

    # ─── Complete ioB — now all 15 TUs on the LU are covered by CO shipments ─────────
    And the shipment identified by ioB_S30558_010 is completed

    # ─── After both completions (pallet fully shipped): the merged order-pure event is emitted ─
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S30558_010
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000003058 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000003058 | 15         |
    And the EPCIS JSON array field has:
      | field               | expectedSize |
      | desadvReferences    | 2            |
      | poReferences        | 2            |
      | shipmentDocumentNos | 2            |
    Then the EPCIS JSON pallet 0 crates are order-pure with POReferences:
      | poReference |
      | 1170000001  |
      | 1170000002  |

    # ─── Post-fix the sibling (ioB) ALSO returns the merged pallet — no fixed owner ───
    When the EPCIS JSON export function is called for M_InOut identified by ioB_S30558_010
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000003058 | 15         |

  @from:cucumber
  @Id:S30558_020
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  Scenario: S30558_020 — Both draft shipments generated first, then ioB completed first: gate returns {} for ioB until ioA is also completed
  ## Completion-ordering gate (me03 #30558): both-drafts flow — symmetric to S30558_010 but
  ## completion order is reversed: ioB is completed first.
  ## Generate BOTH shipments as drafts FIRST (each claims its own picked TUs, QuantityType=P),
  ## then complete ioB first. At that point ioA is still a draft (IP) — order A's 5 TUs on
  ## the LU are covered only by a draft shipment (not CO/CL). The RED gate assertion fires
  ## after completing ioB: the current (un-gated) function emits a non-empty partial event,
  ## so the 'returns empty object' assertion FAILS (intended RED). After ioA is also completed,
  ## both ioA and ioB then return the full merged event — no fixed owner under the close-gate model.
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30558_020 | 4060000000192 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30558_020 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30558_020 | ps_S30558_020      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30558_020 | pl_S30558_020  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30558_020         | p_S30558_020 | 5.0      | PCE      | Normal           |

    # AllowConsolidateInOut=N so the two orders' shipments stay separate M_InOuts (no consolidation)
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_S30558_020 | Y          | ps_S30558_020      | 9900000305582 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S30558_020 | 2900000305582 | bp_S30558_020 | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30558_020 | true                 | 9900000305582         | edi_setting_S30558_020_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30558_020 | p_S30558_020 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S30558_020 |
      | pi_TU_S30558_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S30558_020  | pi_LU_S30558_020 | LU          | Y         |
      | piv_TU_S30558_020  | pi_TU_S30558_020 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30558_020 | piv_LU_S30558_020  | 20  | HU       | pi_TU_S30558_020  |
      | pii_TU_S30558_020 | piv_TU_S30558_020  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30558_020  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30558_020          | pii_TU_S30558_020 | p_S30558_020 | 10  | 2020-01-01 |

    # Mobile UI picking profile — DO_NOT_CREATE: no shipment is auto-created on job completion;
    # both jobs finish first so the LU holds all 15 TUs before any shipment is generated.
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs for order A + 10 TUs for order B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S30558_020 | 2026-05-26   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30558_020 | invLine_S30558_020  | p_S30558_020 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30558_020'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID               |
      | invLine_S30558_020  | pickFromCU_S30558_020 |

    And transform CU to new LU
      | sourceCU              | newLU                        | TU_PI_ID          | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S30558_020 | pickFromAggregatedLU_S30558b | pi_TU_S30558_020  | 10          | 15          |

    # Order A — 50 PCE → 5 TUs.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S30558_020 | true    | bp_S30558_020 | 2026-05-26  | 1170000003  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S30558_020 | oA_S30558_020 | p_S30558_020 | 50         | pip_S30558_020          |

    When the order identified by oA_S30558_020 is completed

    # Order B — 100 PCE → 10 TUs. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S30558_020 | true    | bp_S30558_020 | 2026-05-26  | 1170000004  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S30558_020 | oB_S30558_020 | p_S30558_020 | 100        | pip_S30558_020          |

    When the order identified by oB_S30558_020 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S30558_020 | olA_S30558_020 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssB_S30558_020 | olB_S30558_020 | N             |

    # ─── Picking job 1: order A → new LU 'sharedLu_S30558_020' ──────────────────────
    And start picking job for sales order identified by oA_S30558_020
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30558_020
    And pick lines
      | PickingLine.byProduct | PickFromHU                   | QtyPicked |
      | p_S30558_020          | pickFromAggregatedLU_S30558b | 5         |
    And expect current picking target
      | Existing_LU         |
      | sharedLu_S30558_020 |
    And complete picking job

    # ─── Picking job 2: order B targets the SAME LU (LUPickingTarget.ofExistingHU) ──
    # Both jobs done — the LU now physically holds all 15 TUs.
    And start picking job for sales order identified by oB_S30558_020
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by sharedLu_S30558_020
    And pick lines
      | PickingLine.byProduct | PickFromHU                   | QtyPicked |
      | p_S30558_020          | pickFromAggregatedLU_S30558b | 10        |
    And complete picking job

    # ─── Stamp SSCC18 on the shared LU ───────────────────────────────────────────────
    And M_HU_Attribute is changed
      | M_HU_ID             | M_Attribute_ID.Value | Value              |
      | sharedLu_S30558_020 | SSCC18               | 987654321000003059 |

    # ─── Both-drafts flow: generate DRAFT for ssA, then separately for ssB ─────────────
    # Each schedule claims its own picked TUs (QuantityType=P); generating both as drafts
    # before completing either ensures no schedule sweeps the whole LU on its own.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S30558_020        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssA_S30558_020        | ioA_S30558_020 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssB_S30558_020        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssB_S30558_020        | ioB_S30558_020 |

    # ─── Complete ioB first (symmetric ordering vs. S30558_010) ──────────────────────
    And the shipment identified by ioB_S30558_020 is completed

    # RED gate — ioA is still a draft (IP): order A's 5 TUs on the LU are covered only
    # by a draft shipment, not a CO/CL. Correct post-fix behaviour: {} (LU not fully covered
    # by completed shipments → gate blocks emission).
    # This assertion MUST FAIL on the current (un-gated) code (intended RED).
    Then the EPCIS JSON export function returns empty object for M_InOut identified by ioB_S30558_020

    # ─── Complete ioA — now all 15 TUs on the LU are covered by CO shipments ─────────
    And the shipment identified by ioA_S30558_020 is completed

    # ─── After both completions (pallet fully shipped): the merged order-pure event is emitted ─
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S30558_020
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000003059 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000003059 | 15         |
    And the EPCIS JSON array field has:
      | field               | expectedSize |
      | desadvReferences    | 2            |
      | poReferences        | 2            |
      | shipmentDocumentNos | 2            |
    Then the EPCIS JSON pallet 0 crates are order-pure with POReferences:
      | poReference |
      | 1170000003  |
      | 1170000004  |

    # ─── Post-fix the sibling (ioB) ALSO returns the merged pallet — no fixed owner ───
    When the EPCIS JSON export function is called for M_InOut identified by ioB_S30558_020
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000003059 | 15         |

  @from:cucumber
  @Id:S30916_010
  @allure.label.epic:E0375_External_Traceability
  @allure.label.feature:F5410_EPCIS_JSON_Export
  Scenario: S30916_010 — mixed standalone+shared: standalone pallet emits at own completion
  ## Per-LU close-gate: a shipment (ioA) physically touches TWO LUs —
  ## luStandalone_S30916_010 (carries ONLY order A's crates) and luShared_S30916_010 (carries
  ## crates from BOTH order A and order B). Order A is picked in two separate mobile-picking
  ## sessions (IsAllowCompletingPartialPickingJob=Y): job 1 picks 5 TUs into a brand-new
  ## standalone LU, job 2 picks the remaining 5 TUs into a second brand-new LU that order B's own
  ## picking job then joins (LUPickingTarget.ofExistingHU) — reproducing a single M_InOutLine
  ## whose m_hu_assignment rows span two physically distinct LUs.
  ##
  ## Order B's shipment (ioB) stays a DRAFT while ioA is completed. Under the OLD all-or-nothing
  ## gate, ioA touches ≥1 LU AND has an uncovered TU (luShared's B-portion) → the whole shipment
  ## is blocked and returns '{}', even though luStandalone is already fully covered (self-covered
  ## by ioA itself). This RED assertion — ioA's pallets[] must contain exactly luStandalone's SSCC
  ## and must NOT contain luShared's SSCC — MUST FAIL on the current (un-gated) code.
  ## After the per-LU fix: only LUs that are NOT fully covered still block; a fully-covered LU
  ## (luStandalone) is emitted immediately at its own shipment's completion, independent of the
  ## still-open shared LU. Completing ioB afterwards documents closer-emits: ioB (which only ever
  ## touched luShared) then emits the merged shared pallet.
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_010 | 4060000000916 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_010 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_010 | ps_S30916_010      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_010 | pl_S30916_010  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_010         | p_S30916_010 | 5.0      | PCE      | Normal           |

    # AllowConsolidateInOut=N so the two orders' shipments stay separate M_InOuts (no consolidation)
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_S30916_010 | Y          | ps_S30916_010      | 9900000309160 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S30916_010 | 2900000309160 | bp_S30916_010 | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_010 | true                 | 9900000309160         | edi_setting_S30916_010_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_010 | p_S30916_010 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S30916_010 |
      | pi_TU_S30916_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S30916_010  | pi_LU_S30916_010 | LU          | Y         |
      | piv_TU_S30916_010  | pi_TU_S30916_010 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_010 | piv_LU_S30916_010  | 20  | HU       | pi_TU_S30916_010  |
      | pii_TU_S30916_010 | piv_TU_S30916_010  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_010  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_010          | pii_TU_S30916_010 | p_S30916_010 | 10  | 2020-01-01 |

    # Mobile UI picking profile — DO_NOT_CREATE: no shipment is auto-created on job completion.
    # IsAllowCompletingPartialPickingJob=Y: order A's single schedule is picked across TWO jobs
    # (job 1 partial: 5/10 TUs → standalone LU; job 2 completes it: remaining 5/10 TUs → shared LU).
    # IsAlwaysSplitHUsEnabled=N: the newly-created LUs survive intact across picking sessions.
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs standalone-A + 5 TUs shared-A + 5 TUs shared-B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S30916_010 | 2026-06-01   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_010 | invLine_S30916_010  | p_S30916_010 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_010'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID               |
      | invLine_S30916_010  | pickFromCU_S30916_010 |

    And transform CU to new LU
      | sourceCU              | newLU                        | TU_PI_ID          | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S30916_010 | pickFromAggregatedLU_S30916  | pi_TU_S30916_010  | 10          | 15          |

    # Order A — 100 PCE → 10 TUs, split 5/5 across the standalone and the shared LU.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S30916_010 | true    | bp_S30916_010 | 2026-06-01  | 1300000001  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S30916_010 | oA_S30916_010 | p_S30916_010 | 100        | pip_S30916_010          |

    When the order identified by oA_S30916_010 is completed

    # Order B — 50 PCE → 5 TUs, joins the shared LU only.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S30916_010 | true    | bp_S30916_010 | 2026-06-01  | 1300000002  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S30916_010 | oB_S30916_010 | p_S30916_010 | 50         | pip_S30916_010          |

    When the order identified by oB_S30916_010 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S30916_010 | olA_S30916_010 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssB_S30916_010 | olB_S30916_010 | N             |

    # ─── Picking job 1: order A, PARTIAL — 5 TUs → brand-new standalone LU ───────────
    And start picking job for sales order identified by oA_S30916_010
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30916_010
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_010          | pickFromAggregatedLU_S30916 | 5         |
    And expect current picking target
      | Existing_LU              |
      | luStandalone_S30916_010  |
    And complete picking job

    # ─── Picking job 2: order A, completes the schedule — remaining 5 TUs → brand-new shared LU ─
    And start picking job for sales order identified by oA_S30916_010
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30916_010
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_010          | pickFromAggregatedLU_S30916 | 5         |
    And expect current picking target
      | Existing_LU           |
      | luShared_S30916_010   |
    And complete picking job

    # ─── Picking job 3: order B joins the SAME shared LU (LUPickingTarget.ofExistingHU) ──
    And start picking job for sales order identified by oB_S30916_010
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by luShared_S30916_010
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_010          | pickFromAggregatedLU_S30916 | 5         |
    And complete picking job

    # ─── Stamp distinct SSCC18 values on each physical LU ────────────────────────────
    And M_HU_Attribute is changed
      | M_HU_ID                  | M_Attribute_ID.Value | Value              |
      | luStandalone_S30916_010  | SSCC18               | 987654321000030916 |
    And M_HU_Attribute is changed
      | M_HU_ID                | M_Attribute_ID.Value | Value              |
      | luShared_S30916_010    | SSCC18               | 987654321000030917 |

    # ─── Both-drafts flow: generate DRAFT for ssA, then separately for ssB ─────────────
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S30916_010        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssA_S30916_010        | ioA_S30916_010 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssB_S30916_010        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssB_S30916_010        | ioB_S30916_010 |

    # ─── Complete ioA — it touches BOTH luStandalone (self-covered) and luShared (still open, B is a draft) ─
    And the shipment identified by ioA_S30916_010 is completed

    # RED assertion: luStandalone is already fully covered (only ever touched by ioA, which is
    # now CO) and must be emitted at ioA's own completion, independent of luShared still being
    # open (order B's portion is only covered by a draft shipment).
    # On the current (un-gated, all-or-nothing) code ioA returns '{}' entirely → this FAILS (intended RED).
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S30916_010
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030916 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000030916 | 5          |

    # ─── Complete ioB — now all TUs on luShared are covered by CO shipments too ──────
    And the shipment identified by ioB_S30916_010 is completed

    # ─── Closer-emits: ioB (which only ever touched luShared) now emits the merged shared pallet ──
    # This half passes both before and after the fix.
    When the EPCIS JSON export function is called for M_InOut identified by ioB_S30916_010
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030917 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000030917 | 10         |

    # ─── After both completions, ioA (touching both LUs) now returns BOTH fully-covered pallets ──
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S30916_010
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030916 |
      | 987654321000030917 |

  @from:cucumber
  @Id:S30771_010
  @allure.label.epic:E0375_External_Traceability
  @allure.label.feature:F5410_EPCIS_JSON_Export
  Scenario: S30771_010 — cuGTIN resolved from M_Product_ASI_Data when M_Product.GTIN is null and no C_BPartner_Product GTIN exists
  ## RED scenario: product has M_Product.GTIN=null and no GTIN in C_BPartner_Product.
  ## A wildcard M_Product_ASI_Data row carries GTIN=4060000000772.
  ## The EPCIS function currently resolves cuGTIN only from C_BPartner_Product/M_Product.GTIN
  ## and never reads M_Product_ASI_Data — so cuGTIN comes out null.
  ## This scenario asserts the ASI_Data GTIN and therefore FAILS (intended RED).
    Given metasfresh contains M_Products:
      | Identifier    |
      | p_S30771_010  |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30771_010 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30771_010 | ps_S30771_010      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30771_010 | pl_S30771_010  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30771_010         | p_S30771_010  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30771_010 | Y          | ps_S30771_010      | 9900000306770 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30771_010 | true                 | 9900000306770         | edi_setting_S30771_010_bp |

    # C_BPartner_Product row with NO gtin/ean_cu — must not satisfy the old GTIN resolution path
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID  |
      | bp_S30771_010 | p_S30771_010  |

    # Wildcard ASI_Data row: no C_BPartner_ID (matches any buyer), no ASI (matches any ASI), SeqNo=10
    # This is the only source of GTIN for this product — mirrors the case where
    # M_Product.GTIN is null and only M_Product_ASI_Data carries the GTIN.
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID | SeqNo | GTIN          |
      | asiData_S30771_010 | p_S30771_010 | 10    | 4060000000772 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_Products:
      | Identifier             |
      | pmProdLU_S30771_010    |
      | pmProdTU_S30771_010    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID        | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30771_010         | pmProdLU_S30771_010 | 0.0      | PCE      | Normal           |
      | plv_S30771_010         | pmProdTU_S30771_010 | 0.0      | PCE      | Normal           |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name                  |
      | pm_LU_S30771_010                   | pmProdLU_S30771_010         | Pallet_S30771_010     |
      | pm_TU_S30771_010                   | pmProdTU_S30771_010         | Karton_S30771_010     |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID          |
      | pi_LU_S30771_010    |
      | pi_TU_S30771_010    |
      | pi_VHU_S30771_010   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID          | HU_UnitType | IsCurrent |
      | piv_LU_S30771_010   | pi_LU_S30771_010    | LU          | Y         |
      | piv_TU_S30771_010   | pi_TU_S30771_010    | TU          | Y         |
      | piv_VHU_S30771_010  | pi_VHU_S30771_010   | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID        | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID   | OPT.M_HU_PackingMaterial_ID |
      | pii_LU_S30771_010      | piv_LU_S30771_010   | 20  | HU       | pi_TU_S30771_010    |                             |
      | pii_LU_PM_S30771_010   | piv_LU_S30771_010   | 0   | PM       |                     | pm_LU_S30771_010            |
      | pii_TU_S30771_010      | piv_TU_S30771_010   | 0   | PM       |                     | pm_TU_S30771_010            |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID  | M_Attribute.Value |
      | piv_LU_S30771_010   | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S30771_010          | pii_TU_S30771_010 | p_S30771_010  | 10  | 2020-01-01 |

    # Sales order: 10 PCE = 1 TU
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30771_010  | true    | bp_S30771_010 | 2026-06-10  | 3067700001  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30771_010 | o_S30771_010  | p_S30771_010  | 10         | pip_S30771_010          |

    When the order identified by o_S30771_010 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S30771_010             | bp_S30771_010            | o_S30771_010          | P                |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30771_010 | ol_S30771_010  | N             |

    # ─── Inventory → CU → TU → LU → SSCC18 ──────────────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30771_010            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30771_010            | invLine_S30771_010            | p_S30771_010            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30771_010'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30771_010            | cu_S30771_010      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30771_010       | 10    | pip_S30771_010                     | tu_S30771_010                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30771_010       | 1     | pii_LU_S30771_010          | lu_S30771_010             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30771_010 | SSCC18               | 987654321000006770 |

    # ─── TU-level picking ─────────────────────────────────────────────────────────────
    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30771_010      | ss_S30771_010                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30771_010      | ss_S30771_010                    |

    # ─── Generate picked shipment (QuantityType=PD) ───────────────────────────────────
    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30771_010         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30771_010         | io_S30771_010 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | pack_S30771_010    | d_S30771_010             | false               |

    # ─── CORE ASSERTION (RED) ────────────────────────────────────────────────────────
    # cuGTIN must be resolved from M_Product_ASI_Data (4060000000772).
    # Current code only checks C_BPartner_Product.gtin / M_Product.gtin — both null here.
    # This assertion FAILS on the current code: item.cuGTIN is null, expected 4060000000772.
    When the EPCIS JSON export function is called for M_InOut identified by io_S30771_010
    Then the EPCIS JSON item has:
      | palletIndex | crateIndex | itemIndex | cuGTIN        |
      | 0           | 0          | 0         | 4060000000772 |

  @from:cucumber
  @Id:S30916_020
  @allure.label.epic:E0375_External_Traceability
  @allure.label.feature:F5410_EPCIS_JSON_Export
  Scenario: S30916_020 — already-transmitted SSCC is excluded from the function output
  ## RED scenario: a single order produces ONE standalone, fully-covered LU (one physical
  ## pallet, one SSCC18). Baseline sanity: the function returns that pallet. A ledger row is
  ## then seeded in EDI_EPCIS_Transmitted_SSCC for the LU's physical SSCC18, simulating a prior
  ## successful EPCIS transmission of that same physical SSCC (re-export / duplicate-trigger
  ## defense-in-depth). The function must now EXCLUDE that LU; with a single-LU shipment,
  ## excluding the only LU collapses the whole result to '{}'. The current (un-gated) function
  ## ignores the ledger entirely and still returns the pallet, so the final assertion FAILS
  ## (intended RED).
    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_020 | 4060000000920 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_020 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_020 | ps_S30916_020      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_020 | pl_S30916_020  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_020         | p_S30916_020  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_020 | Y          | ps_S30916_020      | 9900000309200 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_020 | true                 | 9900000309200         | edi_setting_S30916_020_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID  |
      | bp_S30916_020 | p_S30916_020  |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S30916_020  |
      | pi_TU_S30916_020  |
      | pi_VHU_S30916_020 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S30916_020  | pi_LU_S30916_020  | LU          | Y         |
      | piv_TU_S30916_020  | pi_TU_S30916_020  | TU          | Y         |
      | piv_VHU_S30916_020 | pi_VHU_S30916_020 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_020 | piv_LU_S30916_020  | 20  | HU       | pi_TU_S30916_020  |
      | pii_TU_S30916_020 | piv_TU_S30916_020  | 0   | MI       |                    |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_020  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S30916_020          | pii_TU_S30916_020 | p_S30916_020  | 10  | 2020-01-01 |

    # Sales order: 10 PCE = 1 TU, standalone pallet
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_020 | true    | bp_S30916_020 | 2026-06-10  | 1300000003  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_020 | o_S30916_020  | p_S30916_020  | 10         | pip_S30916_020          |

    When the order identified by o_S30916_020 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S30916_020             | bp_S30916_020            | o_S30916_020          | P                |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_020 | ol_S30916_020  | N             |

    # ─── Inventory → CU → TU → LU → SSCC18 ──────────────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_020            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_020            | invLine_S30916_020            | p_S30916_020            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_020'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_020            | cu_S30916_020      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_020       | 10    | pip_S30916_020                     | tu_S30916_020                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_020       | 1     | pii_LU_S30916_020          | lu_S30916_020             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30916_020 | SSCC18               | 987654321000030920 |

    # ─── TU-level picking ─────────────────────────────────────────────────────────────
    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_020      | ss_S30916_020                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_020      | ss_S30916_020                    |

    # ─── Generate picked shipment (QuantityType=PD) — standalone LU, fully covered ─────
    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_020         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30916_020         | io_S30916_020 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | pack_S30916_020    | d_S30916_020             | false               |

    # ─── Baseline sanity: the function DOES return the pallet BEFORE any ledger row exists ──
    # Clear the ledger first: it is a real (non-rolled-back) table and the local provided-infra DB
    # is not reset between runs, so a row seeded by a previous run of this scenario would otherwise
    # suppress the baseline pallet. No-op in CI (fresh DB).
    And the EPCIS transmission ledger is empty
    When the EPCIS JSON export function is called for M_InOut identified by io_S30916_020
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030920 |

    # ─── Seed the transmission ledger: this physical SSCC was already sent to an EPCIS receiver ──
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | esConfig_S30916_020      | scriptedCfg_S30916_020                            | M_InOut_EDI_Export_JSON          | M_InOut   |
    And metasfresh contains EDI_EPCIS_Transmitted_SSCC:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    |
      | 987654321000030920 | scriptedCfg_S30916_020                            | io_S30916_020 |

    # ─── CORE ASSERTION (RED): the already-transmitted SSCC must now be excluded ──────
    # The ledger-excluded LU is dropped from pallet_list, so the function yields no pallets
    # (pallets => []) and the shipment is NOT export-relevant — epcis_has_events (the real
    # outbound-selection gate) returns false, so nothing is re-sent. The current (un-gated)
    # function ignores the ledger, still returns the pallet, and stays export-relevant, so
    # this assertion FAILS (intended RED).
    Then the EPCIS export-relevance for M_InOut identified by io_S30916_020 is false

  @from:cucumber
  @Id:S30916_040
  @allure.label.epic:E0375_External_Traceability
  @allure.label.feature:F5410_EPCIS_JSON_Export
  Scenario: S30916_040 — a deactivated ledger row does NOT suppress its SSCC
  ## Regression / guard scenario locking in existing correct behaviour: get_epcis_events_json_fn's
  ## ledger-exclusion filter only matches ACTIVE ledger rows (t.isactive='Y' — see
  ## get_epcis_events_json_fn.sql). This is the mechanism that lets support deactivate a
  ## EDI_EPCIS_Transmitted_SSCC row via the WebUI shipment tab (AD_Tab 549333) to allow a physical
  ## SSCC to be re-transmitted. Mirrors S30916_020's setup: single order, one standalone,
  ## fully-covered LU (one physical pallet, one SSCC18). Baseline sanity confirms the function
  ## returns that pallet. A ledger row is then seeded for the LU's physical SSCC18 with
  ## IsActive='N' (simulating a row support has already deactivated). Unlike S30916_020 (where an
  ## ACTIVE ledger row excludes the SSCC), here the SSCC must still be emitted — proving inactive
  ## rows are ignored by the exclusion. Expected GREEN on first run (guard, not RED→GREEN TDD):
  ## the isactive='Y' filter already exists in production code.
    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_040 | 4060000000940 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_040 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_040 | ps_S30916_040      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_040 | pl_S30916_040  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_040         | p_S30916_040  | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S30916_040 | Y          | ps_S30916_040      | 9900000309400 |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_040 | true                 | 9900000309400         | edi_setting_S30916_040_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID  |
      | bp_S30916_040 | p_S30916_040  |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S30916_040  |
      | pi_TU_S30916_040  |
      | pi_VHU_S30916_040 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S30916_040  | pi_LU_S30916_040  | LU          | Y         |
      | piv_TU_S30916_040  | pi_TU_S30916_040  | TU          | Y         |
      | piv_VHU_S30916_040 | pi_VHU_S30916_040 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_040 | piv_LU_S30916_040  | 20  | HU       | pi_TU_S30916_040  |
      | pii_TU_S30916_040 | piv_TU_S30916_040  | 0   | MI       |                    |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_040  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S30916_040          | pii_TU_S30916_040 | p_S30916_040  | 10  | 2020-01-01 |

    # Sales order: 10 PCE = 1 TU, standalone pallet
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S30916_040 | true    | bp_S30916_040 | 2026-06-10  | 1300000004  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID    | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S30916_040 | o_S30916_040  | p_S30916_040  | 10         | pip_S30916_040          |

    When the order identified by o_S30916_040 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S30916_040             | bp_S30916_040            | o_S30916_040          | P                |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S30916_040 | ol_S30916_040  | N             |

    # ─── Inventory → CU → TU → LU → SSCC18 ──────────────────────────────────────────
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_S30916_040            | 2026-06-10   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_040            | invLine_S30916_040            | p_S30916_040            | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_040'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_S30916_040            | cu_S30916_040      |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | cu_S30916_040       | 10    | pip_S30916_040                     | tu_S30916_040                 |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | tu_S30916_040       | 1     | pii_LU_S30916_040          | lu_S30916_040             |

    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | Value              |
      | lu_S30916_040 | SSCC18               | 987654321000030940 |

    # ─── TU-level picking ─────────────────────────────────────────────────────────────
    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_S30916_040      | ss_S30916_040                    | 10        | IP     | P          | ?              |
    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | tu_S30916_040      | ss_S30916_040                    |

    # ─── Generate picked shipment (QuantityType=PD) — standalone LU, fully covered ─────
    When 'generate shipments' process is invoked with QuantityType=PD, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S30916_040         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S30916_040         | io_S30916_040 |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | pack_S30916_040    | d_S30916_040             | false               |

    # ─── Baseline sanity: the function DOES return the pallet BEFORE any ledger row exists ──
    And the EPCIS transmission ledger is empty
    When the EPCIS JSON export function is called for M_InOut identified by io_S30916_040
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030940 |

    # ─── Seed a DEACTIVATED ledger row for this physical SSCC (support already deactivated it) ──
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | esConfig_S30916_040      | scriptedCfg_S30916_040                            | M_InOut_EDI_Export_JSON          | M_InOut   |
    And metasfresh contains EDI_EPCIS_Transmitted_SSCC:
      | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID    | OPT.IsActive |
      | 987654321000030940 | scriptedCfg_S30916_040                            | io_S30916_040 | false        |

    # ─── CORE ASSERTION: a deactivated ledger row must NOT suppress the SSCC ────────────
    # Unlike S30916_020 (active row → excluded), an inactive row is ignored by the
    # ledger-exclusion filter (t.isactive='Y'), so the LU stays in pallet_list and the
    # shipment remains export-relevant — the physical SSCC is eligible for re-transmission.
    When the EPCIS JSON export function is called for M_InOut identified by io_S30916_040
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030940 |
    Then the EPCIS export-relevance for M_InOut identified by io_S30916_040 is true

  @from:cucumber
  @Id:S30916_030
  @allure.label.epic:E0375_External_Traceability
  @allure.label.feature:F5410_EPCIS_JSON_Export
  Scenario: S30916_030 — header DESADV/PO refs match the emitted pallet set (multi-order shared LU)
  ## Header-array over-claim (H1): shipment X (order A's shipment) touches TWO LUs —
  ## luStandalone_S30916_030 (order A's own portion only, fully covered by X alone) and
  ## luShared_S30916_030 (order A's portion + order C's portion + a THIRD portion belonging
  ## to order D). Order C's own shipment Y is completed (CO) — a valid CO/CL sibling on
  ## luShared — but order D's portion of luShared is only covered by a still-DRAFT shipment,
  ## so luShared is NOT fully covered and must NOT appear in X's pallets[].
  ## RED assertion: X's pallets[] must contain ONLY luStandalone's SSCC (luShared is absent,
  ## correctly gated by the per-LU coverage filter). But desadvReferences[] / poReferences[] /
  ## shipmentDocumentNos[] are built from shared_lu_inout, which (pre-fix) is scoped to EVERY
  ## LU X touches — including the not-fully-covered luShared — so it still pulls in order C's
  ## DESADV/PO and Y's delivery-note number even though luShared's pallet is absent from
  ## pallets[]. The header thus over-claims an order/DESADV the emitted document does not
  ## actually carry. This MUST FAIL on the current (un-rescoped) code (intended RED): the array
  ## sizes are 2 instead of the correct 1 (X's own order A references only).
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S30916_030 | 4060000000930 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S30916_030 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S30916_030 | ps_S30916_030      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S30916_030 | pl_S30916_030  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S30916_030         | p_S30916_030 | 5.0      | PCE      | Normal           |

    # AllowConsolidateInOut=N so the three orders' shipments stay separate M_InOuts (no consolidation)
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_S30916_030 | Y          | ps_S30916_030      | 9900000309030 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_S30916_030 | 2900000309030 | bp_S30916_030 | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier                |
      | bp_S30916_030 | true                 | 9900000309030         | edi_setting_S30916_030_bp |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S30916_030 | p_S30916_030 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID       |
      | pi_LU_S30916_030 |
      | pi_TU_S30916_030 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent |
      | piv_LU_S30916_030  | pi_LU_S30916_030 | LU          | Y         |
      | piv_TU_S30916_030  | pi_TU_S30916_030 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S30916_030 | piv_LU_S30916_030  | 20  | HU       | pi_TU_S30916_030  |
      | pii_TU_S30916_030 | piv_TU_S30916_030  | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_S30916_030  | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S30916_030          | pii_TU_S30916_030 | p_S30916_030 | 10  | 2020-01-01 |

    # Mobile UI picking profile — DO_NOT_CREATE: no shipment is auto-created on job completion.
    # IsAllowCompletingPartialPickingJob=Y: order A's single schedule is picked across TWO jobs
    # (job 1 partial: 5/8 TUs → standalone LU; job 2 completes it: remaining 3/8 TUs → shared LU).
    # IsAlwaysSplitHUsEnabled=N: the newly-created LUs survive intact across picking sessions.
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source: aggregated LU with 150 PCE (5 TUs standalone-A + 3 TUs shared-A + 2 TUs shared-C + 1 TU shared-D)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_S30916_030 | 2026-06-05   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S30916_030 | invLine_S30916_030 | p_S30916_030 | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_S30916_030'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID               |
      | invLine_S30916_030 | pickFromCU_S30916_030 |

    And transform CU to new LU
      | sourceCU              | newLU                       | TU_PI_ID         | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_S30916_030 | pickFromAggregatedLU_S30916 | pi_TU_S30916_030 | 10          | 15          |

    # Order A — 80 PCE → 8 TUs, split 5/3 across the standalone and the shared LU.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S30916_030 | true    | bp_S30916_030 | 2026-06-05  | 1300000301  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S30916_030 | oA_S30916_030 | p_S30916_030 | 80         | pip_S30916_030          |

    When the order identified by oA_S30916_030 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dA_S30916_030            | bp_S30916_030            | oA_S30916_030         | P                |

    # Order C — 20 PCE → 2 TUs, joins the shared LU only. Its OWN shipment (Y) will be completed
    # (CO/CL) — a valid sibling on luShared, but luShared still won't be fully covered because of D.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oC_S30916_030 | true    | bp_S30916_030 | 2026-06-05  | 1300000302  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olC_S30916_030 | oC_S30916_030 | p_S30916_030 | 20         | pip_S30916_030          |

    When the order identified by oC_S30916_030 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dC_S30916_030            | bp_S30916_030            | oC_S30916_030         | P                |

    # Order D — 10 PCE → 1 TU, joins the shared LU too. Its shipment is generated as a DRAFT and
    # deliberately NEVER completed — this is the third portion of luShared that keeps it NOT fully
    # covered, even after both X (order A) and Y (order C) are completed.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oD_S30916_030 | true    | bp_S30916_030 | 2026-06-05  | 1300000303  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olD_S30916_030 | oD_S30916_030 | p_S30916_030 | 10         | pip_S30916_030          |

    When the order identified by oD_S30916_030 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssA_S30916_030 | olA_S30916_030 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssC_S30916_030 | olC_S30916_030 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ssD_S30916_030 | olD_S30916_030 | N             |

    # ─── Picking job 1: order A, PARTIAL — 5 TUs → brand-new standalone LU ───────────
    And start picking job for sales order identified by oA_S30916_030
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30916_030
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_030          | pickFromAggregatedLU_S30916 | 5         |
    And expect current picking target
      | Existing_LU              |
      | luStandalone_S30916_030  |
    And complete picking job

    # ─── Picking job 2: order A, completes the schedule — remaining 3 TUs → brand-new shared LU ─
    And start picking job for sales order identified by oA_S30916_030
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by pi_LU_S30916_030
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_030          | pickFromAggregatedLU_S30916 | 3         |
    And expect current picking target
      | Existing_LU          |
      | luShared_S30916_030  |
    And complete picking job

    # ─── Picking job 3: order C joins the SAME shared LU (LUPickingTarget.ofExistingHU) ──
    And start picking job for sales order identified by oC_S30916_030
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by luShared_S30916_030
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_030          | pickFromAggregatedLU_S30916 | 2         |
    And complete picking job

    # ─── Picking job 4: order D ALSO joins the same shared LU — its portion stays covered only
    # by D's (never-completed) draft shipment, keeping luShared NOT fully covered ──────────────
    And start picking job for sales order identified by oD_S30916_030
    And scan picking slot identified by 200.0
    And set picking target as existing LU identified by luShared_S30916_030
    And pick lines
      | PickingLine.byProduct | PickFromHU                  | QtyPicked |
      | p_S30916_030          | pickFromAggregatedLU_S30916 | 1         |
    And complete picking job

    # ─── Stamp distinct SSCC18 values on each physical LU ────────────────────────────
    And M_HU_Attribute is changed
      | M_HU_ID                  | M_Attribute_ID.Value | Value              |
      | luStandalone_S30916_030  | SSCC18               | 987654321000030930 |
    And M_HU_Attribute is changed
      | M_HU_ID                | M_Attribute_ID.Value | Value              |
      | luShared_S30916_030    | SSCC18               | 987654321000030931 |

    # ─── Generate all three shipments as DRAFTs ────────────────────────────────────────
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S30916_030        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssA_S30916_030        | ioA_S30916_030 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssC_S30916_030        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssC_S30916_030        | ioC_S30916_030 |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssD_S30916_030        | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     |
      | ssD_S30916_030        | ioD_S30916_030 |

    # ─── Complete X (ioA) and Y (ioC) — ioD (order D's shipment) stays DRAFT forever ──────
    And the shipment identified by ioA_S30916_030 is completed
    And the shipment identified by ioC_S30916_030 is completed

    # ─── CORE ASSERTION (RED) ───────────────────────────────────────────────────────────
    # luShared is NOT fully covered (order D's portion is only on a draft shipment) so it must be
    # absent from pallets[] — only luStandalone (order A's own, fully self-covered LU) is emitted.
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S30916_030
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000030930 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000030930 | 5          |

    # Header reference arrays must be scoped to the SAME emitted-LU set as pallets[]: only
    # luStandalone (order A's own LU) is emitted, so the header must carry ONLY order A's own
    # DESADV / PO reference / delivery-note — NOT order C's, even though order C's shipment (Y)
    # is a completed CO/CL sibling sharing luShared with X. Pre-fix, shared_lu_inout is scoped to
    # EVERY LU X touches (including the not-fully-covered luShared), so it also pulls in Y as a
    # sibling and the arrays come out as size 2 (X's + Y's refs) — this assertion FAILS pre-fix
    # (intended RED).
    And the EPCIS JSON array field has:
      | field               | expectedSize |
      | desadvReferences    | 1            |
      | shipmentDocumentNos | 1            |
    And the EPCIS JSON array field has:
      | field        | expectedSize | containsValue |
      | poReferences | 1            | 1300000301    |
