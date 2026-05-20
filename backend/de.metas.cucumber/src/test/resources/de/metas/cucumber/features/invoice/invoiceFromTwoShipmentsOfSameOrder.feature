@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00702.1_Header_Aggregation
@ghActions:run_on_executor3
Feature: One order, two shipments, two invoices — each invoice carries only its own shipment's qty
  Regression guard for mf15#4139 / me03#29921. Three scenarios that ALL must produce two invoices,
  where each invoice carries only the qty of its own shipment (never the qty of both shipments).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-05-10T08:00:00+02:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    # When true (soft_panda customer default), the IC auto-closes after a partial invoice and
    # subsequent deliveries cannot reopen it. Force false so the scenarios below exercise the
    # canonical partial-invoice flow.
    And set sys config boolean value false for sys config C_Invoice_Candidate_Close_PartiallyInvoiced

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_1           |
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | taxCategory      |
      | plv_1                  | p_2          | 15.0     | PCE      | taxCategory      |

    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | OPT.InvoiceRule |
      | customer_1 | N        | Y          | ps_1               | D               |

  # ============================================================================
  # SCENARIO A — single order line, two partial shipments via QtyToDeliver_Override
  # Order line for 10 PCE; ship 4 then 6 by overriding QtyToDeliver before each
  # generate-shipments call. Each shipment → one invoice with qty 4 / 6 — never 10.
  # ============================================================================
  @from:cucumber
  @Id:MF4139_01
  Scenario: A — 1 order line, 2 partial shipments via QtyToDeliver_Override, 2 invoices

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DeliveryRule | DateOrdered | PreparationDate      |
      | so_1       | true    | customer_1    | wh_1           | F            | 2024-05-10  | 2024-05-09T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | sol_1                     | N             |

    # ---------- First partial shipment: 4 PCE of 10 ----------
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 4                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |

    And metasfresh has date and time 2024-05-15T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | s_s_1                            | D                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 4           | true      |

    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | sol_1                     | 4            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 4            | 10         | 4            | 0           |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 4           | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 0            | 10         | 4            | 4           |

    # ---------- Second partial shipment: the remaining 6 PCE ----------
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 6                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |

    And metasfresh has date and time 2024-05-25T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1, shipment_2 | s_s_1                            | D                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_2            | shipment_2            | p_1                     | 6           | true      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 6            | 10         | 10           | 4           |

    # REGRESSION GUARD: 2nd invoice line MUST be qty 6 only (NOT 10).
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1, invoice_2    |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_2               | invoice_2               | p_1                     | 6           | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 0            | 10         | 10           | 10          |

  # ============================================================================
  # SCENARIO B — single order, two order lines, each shipped separately
  # 1 order with 2 lines (p_1 / p_2). Ship line 1 → invoice it. Ship line 2 →
  # invoice it. Each invoice MUST carry only its own line — never both lines.
  # ============================================================================
  @from:cucumber
  @Id:MF4139_02
  Scenario: B — 1 order with 2 order lines, each shipped + invoiced separately

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DeliveryRule | DateOrdered | PreparationDate      |
      | so_1       | true    | customer_1    | wh_1           | F            | 2024-05-10  | 2024-05-09T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
      | sol_2      | so_1       | p_2          | 6          |
    And the order identified by so_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | sol_1                     | N             |
      | s_s_2      | sol_2                     | N             |

    # ---------- Ship + invoice line 1 (p_1) ----------
    And metasfresh has date and time 2024-05-15T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | s_s_1                            | D                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 10          | true      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | sol_1                     | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      |

    # ---------- Ship + invoice line 2 (p_2) ----------
    And metasfresh has date and time 2024-05-25T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_2            | s_s_2                            | D                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_2            | shipment_2            | p_2                     | 6           | true      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_2                              | sol_2                     | 6            |

    # REGRESSION GUARD: 2nd invoice run is asked to invoice ic_2 only.
    # It MUST NOT re-pick ic_1 (already fully invoiced) onto the new invoice.
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_2                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_2                              | invoice_2               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_2               | invoice_2               | p_2                     | 6           | true      |

  # ============================================================================
  # SCENARIO C — single order line, two HUs each covering part of the qty
  # Order line for 10 PCE. Two virtual HUs (4 PCE and 6 PCE). Pick + ship each
  # HU independently → two shipments → two invoices, each carrying only its
  # HU's qty.
  # ============================================================================
  @from:cucumber
  @Id:MF4139_03
  Scenario: C — 1 order line, 2 HUs (4 + 6) each in its own shipment, 2 invoices

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DeliveryRule | DateOrdered | PreparationDate      |
      | so_1       | true    | customer_1    | wh_1           | F            | 2024-05-10  | 2024-05-09T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | sol_1                     | N             |

    # Two virtual HUs, each carrying part of the order qty.
    # The step def only processes the first row of the table, so call it twice.
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_4               | 4            | s_s_1                            | p_1                     |
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_6               | 6            | s_s_1                            | p_1                     |

    # ---------- Pick + ship HU hu_4 ----------
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_1                            | p_1                     | 4         | hu_4               |
    And metasfresh has date and time 2024-05-15T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | s_s_1                            | P                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 4           | true      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | sol_1                     | 4            |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 4           | true      |

    # ---------- Pick + ship HU hu_6 ----------
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_1                            | p_1                     | 6         | hu_6               |
    And metasfresh has date and time 2024-05-25T08:00:00+02:00[Europe/Berlin]
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1, shipment_2 | s_s_1                            | P                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_2            | shipment_2            | p_1                     | 6           | true      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 6            | 10         | 10           | 4           |

    # REGRESSION GUARD: 2nd invoice line MUST be qty 6 only (NOT 10).
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1, invoice_2    |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_2               | invoice_2               | p_1                     | 6           | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | QtyInvoiced |
      | ic_1                              | 0            | 10         | 10           | 10          |
