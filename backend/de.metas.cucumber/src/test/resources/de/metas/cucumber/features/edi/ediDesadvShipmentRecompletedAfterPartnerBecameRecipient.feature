@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
Feature: EDI DESADV for a shipment re-completed after its partner became a DESADV recipient
## The order and its shipment were completed while the partner was no DESADV recipient.
## Afterwards the partner is set up as DESADV recipient.
## Reactivating and re-completing the shipment must then create the DESADV and link the shipment to it.

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


# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @Id:S32714_TC3
  Scenario: S32714_TC3 - Shipment with HUs completed for a non-recipient partner, partner becomes DESADV recipient, shipment reactivated and re-completed -> DESADV with packs
  Order 4 PCE of a product with TU capacity 2 PCE/TU, so the shipment carries an aggregate HU (2 TU).
  Before the partner is a DESADV recipient: no DESADV, shipment EDI status Don't send.
  After: DESADV exists for the order, shipment is linked to it, shipment EDI status Pending, DESADV pack present.

    Given metasfresh contains M_Products:
      | Identifier |
      | p_S32714   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_S32714  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S32714  | ps_S32714          | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_S32714 | pl_S32714      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S32714             | p_S32714     | 10.0     | PCE      | Normal           |

    # the partner has no C_BPartner_EDI_Setting yet, so it is no DESADV recipient
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S32714  | Y          | ps_S32714          | 9900032714010 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | pi_LU_S32714 |
      | pi_TU_S32714 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID   | HU_UnitType | IsCurrent |
      | piv_LU_S32714      | pi_LU_S32714 | LU          | Y         |
      | piv_TU_S32714      | pi_TU_S32714 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_S32714   | piv_LU_S32714      | 10  | HU       | pi_TU_S32714      |
      | pii_TU_S32714   | piv_TU_S32714      | 0   | PM       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pip_S32714              | pii_TU_S32714   | p_S32714     | 2   | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference           |
      | o_S32714   | true    | bp_S32714     | 2026-03-11  | po_ref_S32714_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S32714  | o_S32714   | p_S32714     | 4          | pip_S32714              |

    When the order identified by o_S32714 is completed

    Then C_Order and its C_OrderLines are not linked to any EDI_Desadv:
      | C_Order_ID |
      | o_S32714   |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_S32714  | ol_S32714      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_S32714             | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_S32714             | io_S32714  | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | movementqty | processed |
      | iol_S32714     | io_S32714  | p_S32714     | 4           | true      |

    # ─── Premise: the shipment was completed for a non-recipient partner ───
    And after not more than 30s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | io_S32714  | N                |
    And C_Order and its C_OrderLines are not linked to any EDI_Desadv:
      | C_Order_ID |
      | o_S32714   |
    And after not more than 30s, there are no records in EDI_Desadv_Pack

    # ─── The partner is now set up as DESADV recipient (partner-wide) ───
    When metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier          |
      | bp_S32714     | true                 | 9900032714010         | edi_setting_S32714_1 |

    # ─── Reactivate and complete the shipment again ───
    And the shipment identified by io_S32714 is reactivated
    And the shipment identified by io_S32714 is completed

    # ─── CORE ASSERTIONS ───
    Then EDI_Desadv is found:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_Desadv_ID.Identifier | OPT.SumDeliveredInStockingUOM | OPT.FulfillmentPercent |
      | bp_S32714                | o_S32714              | desadv_S32714            | 4                             | 100                    |
    And EDI_Desadv_M_InOut records are found:
      | EDI_Desadv_ID | M_InOut_ID | ExpectedRowCountForDesadv |
      | desadv_S32714 | io_S32714  | 1                         |
    And after not more than 30s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | io_S32714  | P                |

    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID | IsManual_IPA_SSCC18 |
      | pack_S32714        | desadv_S32714 | true                |
    And after not more than 60s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | QtyTU | QtyCUsPerTU | QtyCUsPerLU | MovementQty | M_InOut_ID | M_InOutLine_ID |
      | pi_S32714               | pack_S32714        | 2     | 2           | 4           | 4           | io_S32714  | iol_S32714     |
