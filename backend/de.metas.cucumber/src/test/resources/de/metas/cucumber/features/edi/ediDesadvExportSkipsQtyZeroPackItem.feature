@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI
Feature: EDI DESADV export must not include pack items whose own MovementQty is zero
# The EXP_Format (Name: EDI_Exp_Desadv_Pack_Item) WhereClause must filter on the pack
# item's own MovementQty>0, not the parent line's QtyDeliveredInUOM.
# A pack item with MovementQty=0 on a line whose total QtyDeliveredInUOM>0 must be
# excluded from the export.

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
  @Id:S0353_010
  Scenario: Export format must skip pack items with MovementQty=0

    Given metasfresh contains M_Products:
      | Identifier   |
      | product_main |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_S0353_010 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S0353_010 | ps_S0353_010       | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID |
      | plv_S0353_010 | pl_S0353_010   |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_S0353_010 | plv_S0353_010          | product_main | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID | GLN          |
      | endcustomer | Y          | ps_S0353_010       | location_gln |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | Identifier            |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN | edi_setting_S0353_010 |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | product_main            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | order_main | true    | endcustomer   | 2024-06-10  | po_ref_@Date@ |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_main | order_main | product_main | 10         |

    When the order identified by order_main is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched  | orderLine_main            | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched                         | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched                         | shipment              |

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

    # Capture the auto-created EDI_DesadvLine under an identifier
    # Precondition: this DESADV has exactly one line (the scenario creates a single order line)
    Then EDI_DesadvLine records are found:
      | EDI_DesadvLine_ID | EDI_Desadv_ID |
      | desadvLine        | desadv        |

    # Inject an extra pack item with MovementQty=0 into the SAME pack on the same line
    Given metasfresh contains EDI_Desadv_Pack_Item:
      | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | EDI_DesadvLine_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
      | pi_zero                 | packMain           | desadvLine        | 0           | 0           | -              |

    # The export format WhereClause must select pack items by the item's own MovementQty>0,
    # not by the parent line's QtyDeliveredInUOM, so only the qty>0 item is exported.
    Then the DESADV pack-item export-format selects only:
      | EDI_Desadv_ID | EDI_Desadv_Pack_Item_ID |
      | desadv        | pi_nonzero              |
