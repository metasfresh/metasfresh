@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI
Feature: Reactivating and re-completing a shipment must not leave an orphan Qty-0 DESADV pack item
# A shipment is COMPLETED,then REACTIVATED (only a non-quantity field such as M_Tour_ID changed),
# then RE-COMPLETED.
#
# The bug (birth mechanism, DesadvLineSSCC18Generator): when the SSCC generator is invoked with more labels
# than the LU/TU breakdown can supply (e.g. no packing instructions configured — "No Packing Item" / 101),
# TotalQtyCUBreakdownCalculator.NULL is used.  Every subtractOneLU() call returns LUQtys.NULL
# (qtyCUsPerLU=0), so a pack item with MovementQty=0 and M_InOutLine_ID=NULL is created.
# That item becomes a permanent orphan after reactivate->re-complete because:
#   - reactivation cleanup is keyed on M_InOutLine_ID (NULL items are invisible to it)
#   - re-completion cannot reclaim a Qty-0 item (no real shipment line has MovementQty=0)
#
# The fix (DesadvLineSSCC18Generator.generateAndEnqueuePrinting): skip any LU whose breakdown yields
# qtyCUsPerLU=0 — no pack/pack-item is created for that slot, so no orphan is ever born.
#
# This scenario reproduces the BIRTH by invoking real SSCC generation via
# "When SSCC labels are generated for EDI_DesadvLine:" with LabelCount=2 on an order-line that uses
# "No Packing Item" (M_HU_PI_Item_Product_ID=101).  The LU/TU config lookup falls back to
# TotalQtyCUBreakdownCalculator.NULL -> subtractOneLU() returns LUQtys.NULL (qtyCUsPerLU=0).
# After the fix, the generator skips the 0-qty LU -> no orphan is created.
# Then reactivate (M_Tour_ID change) -> re-complete -> assert only the 1 real qty>0 item survives.
# This scenario is RED before the fix and GREEN after — it is the authoritative cucumber
# reproduction of the orphan birth-prevention path.

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
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | Identifier            |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN | edi_setting_S0353_020 |
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

    # Validate the auto-created EDI_Desadv sums
    Then validate created edi desadv
      | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | order_main            | 10                        | 10                      |

    # Register the EDI_Desadv under the 'desadv' identifier so subsequent steps can reference it
    Then EDI_Desadv is found:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_Desadv_ID.Identifier |
      | endcustomer              | order_main            | desadv                   |

    Then EDI_DesadvLine records are found:
      | EDI_DesadvLine_ID | EDI_Desadv_ID |
      | desadvLine        | desadv        |

    # Invoke real SSCC label generation with LabelCount=2 on the desadv line.
    # The order-line uses "No Packing Item" (M_HU_PI_Item_Product_ID=101), so
    # PrintableDesadvLineSSCC18Labels falls back to TotalQtyCUBreakdownCalculator.NULL.
    # Each subtractOneLU() returns LUQtys.NULL (qtyCUsPerLU=0).
    # PRE-FIX: the generator would create 2 pack items with MovementQty=0, M_InOutLine_ID=NULL
    #          (one for each requested label slot that exhausts the calculator).
    # POST-FIX: the generator skips Qty-0 LUs — no orphan pack item is created.
    # The total EDI_Desadv_Pack_Item count MUST remain exactly 1 (the real qty=10 item).
    When SSCC labels are generated for EDI_DesadvLine:
      | EDI_DesadvLine_ID | LabelCount |
      | desadvLine        | 2          |

    # After SSCC generation: the fix ensures no Qty-0 orphan was born.
    # Still exactly 1 pack item (the real qty=10 item from shipment completion).
    Then after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
      | pi_nonzero              | packMain           | 10          | 10          | shipmentLine   |

    # 1) REACTIVATE the shipment (production: a non-quantity field changed)
    When the shipment identified by shipment is reactivated

    # change a non-quantity field (the tour) while the shipment is reactivated
    And update M_InOut:
      | M_InOut_ID | OPT.M_Tour_ID |
      | shipment   | tour          |

    # 2) RE-COMPLETE the shipment
    And the shipment identified by shipment is completed

    # Reactivation removes the old pack (its item was linked by M_InOutLine_ID); re-completion
    # creates a single fresh manual pack. We don't pin SeqNo (it is an implementation detail of
    # the pack sequence) — we just locate the active manual pack so we can assert its item below.
    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier |
      | packRecompleted               | true                | null                   |

    # After the cycle, only the legitimate qty>0 pack item must remain.
    # If a Qty-0/NULL-M_InOutLine_ID orphan had been created by the SSCC generator (pre-fix),
    # it would survive here (it cannot be cleaned up by reactivation or reclaimed on re-complete)
    # and the total-count assertion would fail with 2 items instead of 1.
    Then after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
      | pi_recompleted          | packRecompleted    | 10          | 10          | shipmentLine   |
