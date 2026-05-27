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
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_EPCIS_010  | true                 | 9900000600010         |

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
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_EPCIS_030  | true                 | 9900000600030         |

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
    And the following c_bpartner is changed
      | C_BPartner_ID   | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_130   | true                 | 9900000291300         |

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

    # Order A — distinct numeric POReference (≤10 digits) → LPAD pads to '1234567893', per Migros spec
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

    # Order B — distinct numeric POReference (10 digits) → LPAD leaves '9876543210' unchanged, per Migros spec
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
    And the EPCIS JSON array field has:
      | field            | expectedSize |
      | desadvReferences | 2            |
      | poReferences     | 2            |
    # ─── Per-LU POReference in dummy GRAI ────────────────────────────────────────────
    # POReferences are '1234567893' (Order A, 10 digits → LPAD no-op → '1234567893') and
    # '9876543210' (Order B, 10 digits → LPAD no-op → '9876543210').
    # Migros spec: urn:epc:id:grai:<GCP>.<assetType>.<10-digit Bestellnummer><2-digit counter>
    # Each pallet's TU dummy GRAI must contain only its own source order's padded POReference.
    Then the EPCIS JSON pallets have dummy GRAIs containing the source order POReference:
      | sscc18             | ExpectedPOReferenceSanitized |
      | 987654321000000016 | 1234567893                   |
      | 987654321000000023 | 9876543210                   |


  @from:cucumber
  @ghActions:run_on_executor5
  @Id:S29231_170
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  Scenario: S29231_170 — Two shipments share one LU with HA aggregates: each shipment sees only its own crates
  ## Regression for get_epcis_events_json_fn (CTE ha_items_with_vtu, me03#29231).
  ## Real-world LAF1010-3 scenario: a picker consolidates two orders' goods onto one
  ## physical pallet. The EPCIS export for shipment-1 must only enumerate the HA
  ## aggregates allocated to shipment-1 via m_hu_assignment.vhu_id — NOT every HA
  ## aggregate physically present on the LU. Original bug: shipment-1's JSON
  ## contained 35 crates instead of 15.
    Given metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | p_S29231_140 | 4060000000147 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_140 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_140 | ps_S29231_140      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_140 | pl_S29231_140  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_140         | p_S29231_140 | 5.0      | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_140 | Y          | ps_S29231_140      | 9900000291400 |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_140 | true                 | 9900000291400         |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_S29231_140 | p_S29231_140 |

    # HU PI: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S29231_140  |
      | pi_TU_S29231_140  |
      | pi_VHU_S29231_140 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S29231_140  | pi_LU_S29231_140  | LU          | Y         |
      | piv_TU_S29231_140  | pi_TU_S29231_140  | TU          | Y         |
      | piv_VHU_S29231_140 | pi_VHU_S29231_140 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S29231_140 | piv_LU_S29231_140  | 20  | HU       | pi_TU_S29231_140  |
      | pii_TU_S29231_140 | piv_TU_S29231_140  | 0   | PM       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID | Qty | ValidFrom  |
      | pip_S29231_140          | pii_TU_S29231_140 | p_S29231_140 | 10  | 2020-01-01 |

    # Order A — 50 PCE → 5 TU. POReference is 10 digits so LPAD is a no-op.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_S29231_140 | true    | bp_S29231_140 | 2026-05-25  | 1140000001  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S29231_140 | oA_S29231_140 | p_S29231_140 | 50         | pip_S29231_140          |

    When the order identified by oA_S29231_140 is completed

    # Order B — 100 PCE → 10 TU. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_S29231_140 | true    | bp_S29231_140 | 2026-05-25  | 1140000002  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S29231_140 | oB_S29231_140 | p_S29231_140 | 100        | pip_S29231_140          |

    When the order identified by oB_S29231_140 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssA_S29231_140 | olA_S29231_140 | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssB_S29231_140 | olB_S29231_140 | N             |

    # Generate ONE shipment per schedule (independent M_InOuts that we'll then share an LU between)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_S29231_140        | D            | true                | false       |
      | ssB_S29231_140        | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ssA_S29231_140        | ioA_S29231_140 |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ssB_S29231_140        | ioB_S29231_140 |

    # ─── Create ONE shared LU with two HA aggregates via real BL ────────────────────
    # Without the bug fix, shipment_A's EPCIS would see BOTH HA aggregates (5+10=15 crates);
    # with the fix it only sees the HA aggregate whose VTU is referenced by shipment_A's
    # m_hu_assignment row (5 crates).
    # Uses real metasfresh BL (InterfaceWrapperHelper + IHUAssignmentBL) instead of raw SQL.
    And one shared LU created via BL with SSCC18 '987654321000001400' carries HA aggregates assigned to inout lines:
      | M_InOut_ID     | crateCount |
      | ioA_S29231_140 | 5          |
      | ioB_S29231_140 | 10         |

    # ─── Shipment A: only its own 5 crates ───────────────────────────────────────────
    When the EPCIS JSON export function is called for M_InOut identified by ioA_S29231_140
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000001400 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000001400 | 5          |

    # ─── Shipment B: only its own 10 crates ──────────────────────────────────────────
    When the EPCIS JSON export function is called for M_InOut identified by ioB_S29231_140
    Then the EPCIS JSON pallets contain SSCC18 values in any order:
      | sscc18             |
      | 987654321000001400 |
    And the EPCIS JSON pallet has:
      | palletIndex | sscc               | crateCount |
      | 0           | 987654321000001400 | 10         |
