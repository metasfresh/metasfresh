@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
Feature: EDI DESADV — one DESADV per shipment, aggregating multiple source orders
  ## When a BPartner has IsEdiOneEDIDesadvPerShipment=Y (EdiDESADVSendingMode=E, ExternalSystem config wired),
  ## a single shipment covering multiple orders produces exactly one DESADV per shipment.
  ## Header POReference is set to the single shared value when all orders agree, otherwise left empty.
  ## Legacy mode (mode=R, flag off) creates one DESADV per order at order-complete time (unchanged).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-05-14T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data

  @from:cucumber
  @Id:S29231_010
  Scenario: Single order, single shipment, IsEdiOneEDIDesadvPerShipment on — exactly one DESADV

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue     |
      | extCfg_S29231_010                   | RabbitMQ | ediDesadv_S29231_010    |

    And metasfresh contains M_Products:
      | Identifier   |
      | p_S29231_010 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_010 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_010 | ps_S29231_010      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_010 | pl_S29231_010  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_010         | p_S29231_010 | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_010 | Y          | ps_S29231_010      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_010    | 9900002923101 | bp_S29231_010 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_010 | true                 | 9900002923101         | E                    | true                         | extCfg_S29231_010                  |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_010 | true    | bp_S29231_010 | 2026-05-14  | PO-ALPHA    |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID | QtyEntered |
      | ol_S29231_010 | o_S29231_010 | p_S29231_010 | 10         |

    When the order identified by o_S29231_010 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S29231_010 | ol_S29231_010  | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_010         | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_010         | io_S29231_010 |

    # One DESADV per shipment: exactly one EDI_Desadv_Pack must exist
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_010    | true                |


  @from:cucumber
  @Id:S29231_020
  Scenario: Two orders with the same POReference, flag on — one DESADV per shipment

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue     |
      | extCfg_S29231_020                   | RabbitMQ | ediDesadv_S29231_020    |

    And metasfresh contains M_Products:
      | Identifier     |
      | p_S29231_020_A |
      | p_S29231_020_B |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_020 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_020 | ps_S29231_020      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_020 | pl_S29231_020  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_020         | p_S29231_020_A | 10.0     | PCE      | Normal                        |
      | plv_S29231_020         | p_S29231_020_B | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_020 | Y          | ps_S29231_020      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_020    | 9900002923102 | bp_S29231_020 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_020 | true                 | 9900002923102         | E                    | true                         | extCfg_S29231_020                  |

    # Both orders share the same POReference
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_020_A | true    | bp_S29231_020 | 2026-05-14  | PO-BRAVO    |
      | o_S29231_020_B | true    | bp_S29231_020 | 2026-05-14  | PO-BRAVO    |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_S29231_020_A | o_S29231_020_A | p_S29231_020_A | 5          |
      | ol_S29231_020_B | o_S29231_020_B | p_S29231_020_B | 5          |

    When the order identified by o_S29231_020_A is completed
    And the order identified by o_S29231_020_B is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ss_S29231_020_A | ol_S29231_020_A | N             |
      | ss_S29231_020_B | ol_S29231_020_B | N             |

    # New mode: one DESADV per shipment — generate first order, assert one pack
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_020_A       | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ss_S29231_020_A       | io_S29231_020_A |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_020_A  | true                |

    # Generate second order, assert total reaches two packs
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_020_B       | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ss_S29231_020_B       | io_S29231_020_B |

    # Two shipments → two DESADV packs total (one per shipment)
    And after not more than 60s, there are exactly 2 EDI_Desadv_Pack records


  @from:cucumber
  @Id:S29231_030
  Scenario: Two orders with different POReferences, flag on — one DESADV per shipment

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue     |
      | extCfg_S29231_030                   | RabbitMQ | ediDesadv_S29231_030    |

    And metasfresh contains M_Products:
      | Identifier     |
      | p_S29231_030_A |
      | p_S29231_030_B |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_030 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_030 | ps_S29231_030      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_030 | pl_S29231_030  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_030         | p_S29231_030_A | 10.0     | PCE      | Normal                        |
      | plv_S29231_030         | p_S29231_030_B | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_030 | Y          | ps_S29231_030      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_030    | 9900002923103 | bp_S29231_030 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_030 | true                 | 9900002923103         | E                    | true                         | extCfg_S29231_030                  |

    # Two orders with different POReferences
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_030_A | true    | bp_S29231_030 | 2026-05-14  | PO-CHARLIE  |
      | o_S29231_030_B | true    | bp_S29231_030 | 2026-05-14  | PO-DELTA    |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_S29231_030_A | o_S29231_030_A | p_S29231_030_A | 5          |
      | ol_S29231_030_B | o_S29231_030_B | p_S29231_030_B | 5          |

    When the order identified by o_S29231_030_A is completed
    And the order identified by o_S29231_030_B is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ss_S29231_030_A | ol_S29231_030_A | N             |
      | ss_S29231_030_B | ol_S29231_030_B | N             |

    # New mode: one DESADV per shipment — generate first order, assert one pack
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_030_A       | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ss_S29231_030_A       | io_S29231_030_A |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_030_A  | true                |

    # Generate second order, assert total reaches two packs
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_030_B       | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ss_S29231_030_B       | io_S29231_030_B |

    # Two shipments → two DESADV packs total (one per shipment)
    And after not more than 60s, there are exactly 2 EDI_Desadv_Pack records


  @from:cucumber
  @Id:S29231_040
  Scenario: One order, two partial shipments, flag on — two DESADVs (one per shipment)

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue     |
      | extCfg_S29231_040                   | RabbitMQ | ediDesadv_S29231_040    |

    And metasfresh contains M_Products:
      | Identifier   |
      | p_S29231_040 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_040 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_040 | ps_S29231_040      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_040 | pl_S29231_040  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_040         | p_S29231_040 | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_040 | Y          | ps_S29231_040      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_040    | 9900002923104 | bp_S29231_040 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_040 | true                 | 9900002923104         | E                    | true                         | extCfg_S29231_040                  |

    # Create stock so that after partial delivery the schedule remains deliverable (DeliveryRule=A requires available stock)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID  | MovementDate | M_Warehouse_ID |
      | inv_S29231_040  | 2026-05-14   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID  | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_S29231_040  | inv_l_S29231_040    | p_S29231_040 | 0       | 10       | PCE          |
    When the inventory identified by inv_S29231_040 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID          |
      | inv_l_S29231_040    | hu_S29231_040    |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_040 | true    | bp_S29231_040 | 2026-05-14  | PO-GOLF     |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID | QtyEntered |
      | ol_S29231_040 | o_S29231_040 | p_S29231_040 | 10         |

    When the order identified by o_S29231_040 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S29231_040 | ol_S29231_040  | N             |

    # First partial shipment: deliver 6 out of 10 using WP-level override
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | ss_S29231_040         | D            | true                | false       | 6                                               |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ss_S29231_040         | io_S29231_040_A |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_040_A  | true                |

    # Wait for schedule recompute so QtyToDeliver reflects the 4 remaining
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_S29231_040 | ol_S29231_040  | N             |

    # Second partial shipment: deliver remaining 4 — no override needed, QtyToDeliver=4 after recompute
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S29231_040         | D            | true                | false       |

    Then after not more than 120s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | ss_S29231_040         | io_S29231_040_B | io_S29231_040_A                         |

    # Two DESADVs = two packs total (one pack per DESADV per shipment)
    And after not more than 120s, there are exactly 2 EDI_Desadv_Pack records


  @from:cucumber
  @Id:S29231_050
  Scenario: Legacy regression — two orders with different POReferences, flag off — two DESADVs (one per order, at order-complete time)

    And metasfresh contains M_Products:
      | Identifier     |
      | p_S29231_050_A |
      | p_S29231_050_B |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_050 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_050 | ps_S29231_050      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_050 | pl_S29231_050  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_050         | p_S29231_050_A | 10.0     | PCE      | Normal                        |
      | plv_S29231_050         | p_S29231_050_B | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_050 | Y          | ps_S29231_050      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_050    | 9900002923105 | bp_S29231_050 | true            | true     |
    # Legacy mode: R sending mode, flag off. Two DESADVs are created at order-complete time (one per POReference).
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment |
      | bp_S29231_050 | true                 | 9900002923105         | R                    | false                        |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_050_A | true    | bp_S29231_050 | 2026-05-14  | PO-ECHO     |
      | o_S29231_050_B | true    | bp_S29231_050 | 2026-05-14  | PO-FOXTROT  |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_S29231_050_A | o_S29231_050_A | p_S29231_050_A | 5          |
      | ol_S29231_050_B | o_S29231_050_B | p_S29231_050_B | 5          |

    When the order identified by o_S29231_050_A is completed
    And the order identified by o_S29231_050_B is completed

    # Legacy: two separate DESADVs created at order-complete time — one per distinct POReference
    And validate created edi desadv
      | Identifier    | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | desadv_050_A  | o_S29231_050_A        | 0                         | 5                       |
      | desadv_050_B  | o_S29231_050_B        | 0                         | 5                       |


  @from:cucumber
  @Id:S29231_060
  Scenario: Two orders with different POReferences aggregated into a single shipment — one DESADV with empty header POReference

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue     |
      | extCfg_S29231_060                   | RabbitMQ | ediDesadv_S29231_060    |

    And metasfresh contains M_Products:
      | Identifier     |
      | p_S29231_060_A |
      | p_S29231_060_B |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_060 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_060 | ps_S29231_060      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_060 | pl_S29231_060  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_060         | p_S29231_060_A | 10.0     | PCE      | Normal                        |
      | plv_S29231_060         | p_S29231_060_B | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_060 | Y          | ps_S29231_060      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_060    | 9900002923106 | bp_S29231_060 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_060 | true                 | 9900002923106         | E                    | true                         | extCfg_S29231_060                  |

    # Two orders with different POReferences — same BPartner, location, warehouse → eligible for shipment aggregation
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_060_A | true    | bp_S29231_060 | 2026-05-14  | PO-HOTEL    |
      | o_S29231_060_B | true    | bp_S29231_060 | 2026-05-14  | PO-INDIA    |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered |
      | ol_S29231_060_A | o_S29231_060_A | p_S29231_060_A | 5          |
      | ol_S29231_060_B | o_S29231_060_B | p_S29231_060_B | 5          |

    When the order identified by o_S29231_060_A is completed
    And the order identified by o_S29231_060_B is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ss_S29231_060_A | ol_S29231_060_A | N             |
      | ss_S29231_060_B | ol_S29231_060_B | N             |

    # Batch — both schedules enqueued in ONE workpackage so the shipment generator merges them into a single M_InOut
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S29231_060_A       |
      | ss_S29231_060_B       |

    # Look up the InOut from each schedule — the second call asserts the alias still resolves
    # to the same M_InOut (one shipment covering both schedules)
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_060_A       | io_S29231_060 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_060_B       | io_S29231_060 |

    # One shipment → one DESADV → two DesadvLines (one per source orderLine).
    # Pack count is two because EDIDesadvPackService.createOrExtendPacks creates one pack
    # per inOutLine in the no-HU configuration of this test (createPackUsingJustInOutLine
    # is invoked once per call; "extend" only kicks in for the HU path). With HUs configured
    # in production data the two inOutLines could share a single LU and produce one pack —
    # but this scenario does not set up HUs.
    And after not more than 60s, there are exactly 2 EDI_Desadv_Pack records

    # The single DESADV linked to the shipment has empty header POReference (source orders disagree)
    # and exactly two DesadvLines (one per source orderLine — me03#29231 per-shipment mode
    # allocates a DESADV-scoped Line via MAX(Line)+10 so the per-order Line=10 collision
    # observed pre-fix is gone).
    And the EDI_Desadv linked to M_InOut has the following properties:
      | M_InOut_ID    | OPT.POReference | OPT.LineCount |
      | io_S29231_060 |                 | 2             |


  @from:cucumber
  @Id:S29231_070
  Scenario: Two orders into one shipment with HU packing — one LU pack with one TU pack-item per source orderLine
  ## Models the customer's real production setup (me03#29231):
  ## - Two orders from EDI, each carrying its own Kd-Bestellnummer (POReference) and product.
  ## - Warehouse aggregates both orders into a single shipment AND packs both products into
  ##   one shared LU (pallet), with one TU per orderLine (one product per TU).
  ## - DESADV must reflect: 1 EDI_Desadv (per-shipment), 2 EDI_DesadvLines (one per source
  ##   orderLine), 1 EDI_Desadv_Pack (the LU with SSCC18), 2 pack-items (one per TU).
  ##
  ## The EDIDesadvPackService.createOrExtendPacks "extend" branch is the one that produces a
  ## single LU pack across multiple inOutLines: when two inOutLines map to the same top-level
  ## M_HU (the LU), the second call extends the first pack instead of creating a new one.

    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue  |
      | extCfg_S29231_070                   | RabbitMQ | ediDesadv_S29231_070 |

    And metasfresh contains M_Products:
      | Identifier     |
      | p_S29231_070_A |
      | p_S29231_070_B |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_S29231_070 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_070 | ps_S29231_070      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_S29231_070 | pl_S29231_070  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID.InternalName |
      | plv_S29231_070         | p_S29231_070_A | 10.0     | PCE      | Normal                        |
      | plv_S29231_070         | p_S29231_070_B | 10.0     | PCE      | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | bp_S29231_070 | Y          | ps_S29231_070      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | loc_070    | 9900002923107 | bp_S29231_070 | true            | true     |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | IsEdiOneEDIDesadvPerShipment | EdiDESADV_ExternalSystem_Config_ID |
      | bp_S29231_070 | true                 | 9900002923107         | E                    | true                         | extCfg_S29231_070                  |

    # HU packing instruction hierarchy: one LU type (capacity = 10 TUs), one TU type (PM).
    # Both products share the same TU template; capacity per TU = order qty (5 PCE) so each
    # orderLine fills exactly one TU.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S29231_070  |
      | pi_TU_S29231_070  |
      | pi_VHU_S29231_070 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S29231_070  | pi_LU_S29231_070  | LU          | Y         |
      | piv_TU_S29231_070  | pi_TU_S29231_070  | TU          | Y         |
      | piv_VHU_S29231_070 | pi_VHU_S29231_070 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID    | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S29231_070  | piv_LU_S29231_070  | 10  | HU       | pi_TU_S29231_070  |
      | pii_TU_S29231_070  | piv_TU_S29231_070  | 0   | PM       |                    |
    # One M_HU_PI_Item_Product per product, both pointing to the SAME TU template — this is
    # what lets the warehouse mix the two products into one shared LU.
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID   | Qty | ValidFrom  |
      | pip_S29231_070_A        | pii_TU_S29231_070 | p_S29231_070_A | 5   | 2020-01-01 |
      | pip_S29231_070_B        | pii_TU_S29231_070 | p_S29231_070_B | 5   | 2020-01-01 |

    # Two orders with different POReferences (Kd-Bestellnummern) — same BPartner, location, warehouse →
    # eligible for shipment aggregation.
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | o_S29231_070_A | true    | bp_S29231_070 | 2026-05-14  | PO-PAPA     |
      | o_S29231_070_B | true    | bp_S29231_070 | 2026-05-14  | PO-QUEBEC   |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID     | M_Product_ID   | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S29231_070_A | o_S29231_070_A | p_S29231_070_A | 5          | pip_S29231_070_A        |
      | ol_S29231_070_B | o_S29231_070_B | p_S29231_070_B | 5          | pip_S29231_070_B        |

    When the order identified by o_S29231_070_A is completed
    And the order identified by o_S29231_070_B is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID  | IsToRecompute |
      | ss_S29231_070_A | ol_S29231_070_A | N             |
      | ss_S29231_070_B | ol_S29231_070_B | N             |

    # Batch — both schedules enqueued in ONE workpackage so the shipment generator merges
    # them into a single M_InOut. With matching TU template both inOutLines should end up
    # under one shared LU.
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S29231_070_A       |
      | ss_S29231_070_B       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_070_A       | io_S29231_070 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_070_B       | io_S29231_070 |

    # ── Customer expectation ──
    # The shared LU produces exactly ONE EDI_Desadv_Pack; the two TUs (one per inOutLine) become
    # two pack-items underneath. The two source orderLines yield two EDI_DesadvLines on the
    # single DESADV (DESADV-scoped Line numbers 10 and 20 via the me03#29231 fresh-line allocator).
    And after not more than 60s, there are exactly 1 EDI_Desadv_Pack records

    And the EDI_Desadv linked to M_InOut has the following properties:
      | M_InOut_ID    | OPT.POReference | OPT.LineCount |
      | io_S29231_070 |                 | 2             |
