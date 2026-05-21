@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI_DESADV_InOut_Link
Feature: EDI DESADV multi-order aggregated shipment — all source orders' DESADVs are exported
## F00353: EDI DESADV InOut Link
## me03#29231 — For a shipment that covers N source orders (all EDI-configured),
## the export view M_InOut_Export_EDI_DESADV_JSON_V must emit N rows — one per source
## order's DESADV — rather than the pre-fix behaviour of emitting only the first
## source order's DESADV.
##
## TC1 (S29231_100): 2 orders → 1 consolidated shipment → view returns 2 DESADV JSON rows.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2026-05-20T10:00:00+02:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |


  @Id:S29231_100
  @from:cucumber
  Scenario: S29231_100 — Two orders, one consolidated shipment → export view emits two DESADV JSONs
  ## me03#29231 — TC1: regression test for the multi-source-order DESADV export fix.
  ## Two EDI-configured orders for the same BPartner, each with a distinct POReference,
  ## are completed (creating one EDI_Desadv per order). Their shipment schedules are
  ## consolidated into a single M_InOut. After the Option-A junction fix the export view
  ## M_InOut_Export_EDI_DESADV_JSON_V must return EXACTLY 2 rows for that M_InOut —
  ## one per source-order DESADV — each carrying its own POReference and EDI_Desadv_ID.

    Given metasfresh contains M_Products:
      | Identifier      |
      | p_S29231_100    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_S29231_100   |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_100   | ps_S29231_100      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID  |
      | plv_S29231_100  | pl_S29231_100   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_100         | p_S29231_100   | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient — the gate condition for DESADV creation at order-complete
    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_100   | Y          | ps_S29231_100      | 9900000290010 |
    And the following c_bpartner is changed
      | C_BPartner_ID  | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_100  | true                 | 9900000290010         |

    # HU packing: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S29231_100  |
      | pi_TU_S29231_100  |
      | pi_VHU_S29231_100 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S29231_100   | pi_LU_S29231_100  | LU          | Y         |
      | piv_TU_S29231_100   | pi_TU_S29231_100  | TU          | Y         |
      | piv_VHU_S29231_100  | pi_VHU_S29231_100 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID     | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID  |
      | pii_LU_S29231_100   | piv_LU_S29231_100   | 20  | HU       | pi_TU_S29231_100   |
      | pii_TU_S29231_100   | piv_TU_S29231_100   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S29231_100          | pii_TU_S29231_100 | p_S29231_100  | 10  | 2020-01-01 |

    # Order A — distinct POReference so it gets its own EDI_Desadv at order-complete
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference                 |
      | oA_S29231    | true    | bp_S29231_100  | 2026-05-20  | PO_A_S29231_100_@Date@      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S29231    | oA_S29231   | p_S29231_100  | 10         | pip_S29231_100          |

    When the order identified by oA_S29231 is completed

    # Order A must now have an EDI_Desadv_ID set (precondition for the export fix to matter)
    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dA_S29231                | bp_S29231_100            | oA_S29231             | P                |

    # Order B — different POReference → its own distinct EDI_Desadv
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference                 |
      | oB_S29231    | true    | bp_S29231_100  | 2026-05-20  | PO_B_S29231_100_@Date@      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S29231    | oB_S29231   | p_S29231_100  | 10         | pip_S29231_100          |

    When the order identified by oB_S29231 is completed

    # Order B must also have its own EDI_Desadv_ID (distinct from A's)
    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dB_S29231                | bp_S29231_100            | oB_S29231             | P                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Both shipment schedules must be ready before batching
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssA_S29231    | olA_S29231     | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssB_S29231    | olB_S29231     | N             |

    # Batch-generate ONE shipment covering both schedules (the aggregated M_InOut)
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ssA_S29231            |
      | ssB_S29231            |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ssA_S29231            | io_S29231_100   |

    # Wait for DESADV pack records to be written (async, triggered by M_InOut BEFORE_COMPLETE)
    # IsManual_IPA_SSCC18=true: this scenario does not set up M_HU_Attribute SSCC18 (no
    # real picked-LU); EDIDesadvPackService.createPackUsingJustInOutLine synthesises the SSCC
    # and sets IsManual_IPA_SSCC18=true. Pack-side SSCC sourced-from-LU is exercised
    # separately in ediDesadvAggregateHU.feature.
    # Consolidated multi-source-order shipment: production creates ONE pack per source DESADV.
    # Disambiguate by EDI_Desadv_ID so each row matches exactly one pack.
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | packA_S29231       | dA_S29231                | true                |
      | packB_S29231       | dB_S29231                | true                |

    # ─── CORE ASSERTION (TC1 falsifiable predicate) ───────────────────────────
    # After the Option-A junction fix the view must return exactly 2 rows for the
    # consolidated shipment — one per source-order DESADV.
    # Before the fix: only 1 row (M_InOut.EDI_Desadv_ID single-FK → only Order-A's DESADV).
    Then the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by io_S29231_100 has:
      | ExpectedRowCount | DistinctDesadvIds | OrderA_Identifier | OrderB_Identifier |
      | 2                | 2                 | oA_S29231         | oB_S29231         |


  @Id:S29231_110
  @from:cucumber
  Scenario: S29231_110 — One order, one shipment → export view emits exactly one DESADV JSON (1-order regression)
  ## me03#29231 — TC2: Single-order baseline regression. A single EDI-configured order is
  ## completed and its shipment schedule generates one M_InOut. After the Option-A junction
  ## fix the export view M_InOut_Export_EDI_DESADV_JSON_V must still return exactly 1 row
  ## for that M_InOut — proving the fix does not break the pre-existing 1-source-order case.

    Given metasfresh contains M_Products:
      | Identifier      |
      | p_S29231_110    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_S29231_110   |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_110   | ps_S29231_110      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID  |
      | plv_S29231_110  | pl_S29231_110   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_110         | p_S29231_110  | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_110   | Y          | ps_S29231_110      | 9900000291100 |
    And the following c_bpartner is changed
      | C_BPartner_ID  | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_110  | true                 | 9900000291100         |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID          |
      | pi_LU_S29231_110    |
      | pi_TU_S29231_110    |
      | pi_VHU_S29231_110   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID          | HU_UnitType | IsCurrent |
      | piv_LU_S29231_110   | pi_LU_S29231_110    | LU          | Y         |
      | piv_TU_S29231_110   | pi_TU_S29231_110    | TU          | Y         |
      | piv_VHU_S29231_110  | pi_VHU_S29231_110   | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID     | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID   |
      | pii_LU_S29231_110   | piv_LU_S29231_110   | 20  | HU       | pi_TU_S29231_110    |
      | pii_TU_S29231_110   | piv_TU_S29231_110   | 0   | PM       |                     |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S29231_110          | pii_TU_S29231_110 | p_S29231_110  | 10  | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference              |
      | o_S29231_110 | true    | bp_S29231_110  | 2026-05-20  | PO_S29231_110_@Date@     |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S29231_110 | o_S29231_110 | p_S29231_110  | 10         | pip_S29231_110          |

    When the order identified by o_S29231_110 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S29231_110             | bp_S29231_110            | o_S29231_110          | P                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ss_S29231_110  | ol_S29231_110  | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S29231_110         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_110         | io_S29231_110 |

    # IsManual_IPA_SSCC18=true: no M_HU_Attribute SSCC18 in this scenario — see TC1 note.
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_110    | true                |

    # ─── CORE ASSERTION (TC2 regression predicate) ────────────────────────────
    # View must still return exactly 1 row for the single-order case.
    Then the M_InOut_Export_EDI_DESADV_JSON_V export view for M_InOut identified by io_S29231_110 has exactly 1 row matching:
      | Order_Identifier |
      | o_S29231_110     |


  # me03#29231 TC3 (S29231_120: N=3 generalisation) — removed per PR #24042 review #4335557991
  # comment on this file:371. The N=3 case exercises the same production code path as TC1
  # (DesadvBL.addToDesadvCreateForInOutIfNotExist's per-line loop + sequencesByDesadv map);
  # one extra iteration adds no new branch coverage. TC1 (S29231_100) is the canonical
  # multi-source-order test; TC2 (S29231_110) is the 1-order regression baseline.
