@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI
Feature: Reactivating and re-completing a shipment must not leave an orphan Qty-0 DESADV pack item
# me03 #29278: a shipment is COMPLETED, then REACTIVATED (only a non-quantity field such as M_Tour_ID changed),
# then RE-COMPLETED. A pre-existing "drafted" pack item (created by SSCC-label generation, with M_InOutLine_ID=NULL)
# whose MovementQty is 0 can never be reclaimed (no real shipment line has MovementQty 0) and is invisible to the
# reactivation cleanup (which deletes pack items filtered by M_InOutLine_ID). It therefore survives forever as an
# orphan Qty-0 pack item in its own phantom pack, which then breaks the EDI export.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2024-06-10T13:30:13+02:00[Europe/Berlin]
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
  @Id:S0353_020
  Scenario: A reactivate (M_Tour_ID change) -> re-complete cycle leaves no orphan Qty-0 pack item

    Given metasfresh contains M_Products:
      | Identifier   |
      | product_main |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0353_020 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0353_020 | ps_S0353_020       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0353_020 | pl_S0353_020   |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_S0353_020 | plv_S0353_020          | product_main | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID | GLN          |
      | endcustomer | Y          | ps_S0353_020       | location_gln |
    And the following c_bpartner is changed
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | product_main            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | order_main | true    | endcustomer   | 2024-06-10  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_main | order_main | product_main | 10         |

    # A tour used to trigger reactivation by changing a non-quantity field on the shipment.
    And metasfresh contains M_Tour:
      | Identifier | OPT.Name |
      | tour       | Tour Mon |

    When the order identified by order_main is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched  | orderLine_main            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched                        | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched                        | shipment              |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine              | shipment              | product_main            | 10          | true      | orderLine_main                |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | packMain                      | true                | null                   | null                                 | null                     | 1         |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerTU | QtyCUsPerTU_InInvoiceUOM | QtyCUsPerLU | QtyCUsPerLU_InInvoiceUOM | QtyItemCapacity | OPT.QtyTU | OPT.M_InOut_ID.Identifier | M_InOutLine_ID | BestBeforeDate | LotNumber | M_HU_PackagingCode_TU_ID | GTIN_TU_PackingMaterial |
      | pi_nonzero              | packMain           | 10          | 10          | 10                       | 10          | 10                       | 0               | 1         | shipment                  | shipmentLine   | null           | null      | null                     | null                    |

    # Capture the auto-created EDI_Desadv and its single line.
    Then validate created edi desadv
      | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM | EDI_Desadv_ID.Identifier |
      | order_main            | 10                        | 10                      | desadv                   |

    Then EDI_DesadvLine records are found:
      | EDI_DesadvLine_ID | EDI_Desadv_ID |
      | desadvLine        | desadv        |

    # Pre-existing "drafted" pack item from SSCC-label generation: M_InOutLine_ID=NULL and MovementQty=0.
    # It sits in the existing pack (packMain). Because its M_InOutLine_ID is NULL it is invisible to the
    # reactivation cleanup (which deletes pack items filtered by M_InOutLine_ID), and because its MovementQty
    # is 0 it can never be reclaimed on re-completion (no real shipment line has MovementQty 0).
    Given metasfresh contains EDI_Desadv_Pack_Item:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | EDI_DesadvLine_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
      | pi_orphan               | packMain           | desadvLine        | 0           | 0           | -              |

    # 1) REACTIVATE the shipment (production: a non-quantity field changed)
    When the shipment identified by shipment is reactivated

    # change a non-quantity field (the tour) while the shipment is reactivated
    And update M_InOut:
      | M_InOut_ID | OPT.M_Tour_ID |
      | shipment   | tour          |

    # 2) RE-COMPLETE the shipment
    And the shipment identified by shipment is completed

    # Re-completion deletes packMain's real item (it had an M_InOutLine_ID) and re-creates the real
    # qty>0 item in a fresh pack. packMain (SeqNo 1) survives so the fresh pack gets SeqNo 2.
    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.SeqNo |
      | packRecompleted               | true                | null                   | 2         |

    # After the cycle, only the legitimate qty>0 pack item must remain.
    # If the orphan Qty-0 / NULL-M_InOutLine_ID item (pi_orphan) survives, the total-count assertion fails.
    Then after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
      | pi_recompleted          | packRecompleted    | 10          | 10          | shipmentLine   |
