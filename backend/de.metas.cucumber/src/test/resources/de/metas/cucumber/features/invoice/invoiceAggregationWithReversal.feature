@from:cucumber
@ghActions:run_on_executor3
Feature: Invoice aggregation of 2 IC2 when one IC was previously invoiced and reversed

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

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
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |
      | plv_1                  | p_2          | 15.0     | PCE      |

    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | customer_1 | N        | Y          | ps_1               |                      |

  @from:cucumber
  Scenario: Order with 2 order lines shipped on same day. One IC invoiced and reversed. Both invoiced with IsDeliveryDateAsInvoiceDate = 'Y' creates a single invoice

    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | so_2       | true    | customer_1    | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_2_1    | so_2       | p_1          | 10         |
      | sol_2_2    | so_2       | p_2          | 5          |
    And the order identified by so_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_s_2_1    | sol_2_1        | N             |
      | s_s_2_2    | sol_2_2        | N             |

    # Drain the material event queue so invoice-candidate async creation completes before the poll.
    # Background sets SKIP_WP_PROCESSOR_FOR_AUTOMATION=true, forcing async via RabbitMQ. Under CI load
    # this poll occasionally exhausts its 60 s budget, cascading to the step-def's 120 s recompute
    # fallback, which can in turn time out and abort the runner before any test completes
    # ("Tests run: 0").
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID | QtyToInvoice |
      | ic_2_1                 | sol_2_1        | 0            |
      | ic_2_2                 | sol_2_2        | 0            |

    # Ship first order line on 2021-04-20 08:00
    And metasfresh has date and time 2021-04-20T08:00:00+01:00[Europe/Berlin]
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2_1               | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_s_2_1               | s_2_1      |

    # Validate first invoice candidate after first shipment
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | DeliveryDate |
      | ic_2_1                            | 10           | 10         | 10           | 2021-04-20   |

    # Create invoice for first candidate with IsDeliveryDateAsInvoiceDate
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IsDeliveryDateAsInvoiceDate |
      | ic_2_1                 | Y                           |

    # Verify first invoice was created
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID  |
      | ic_2_1                 | invoice_2_tmp |

    And validate created invoices
      | Identifier    | C_BPartner_ID | processed | DocStatus |
      | invoice_2_tmp | customer_1    | true      | CO        |

    # Reverse the first invoice
    And the invoice identified by invoice_2_tmp is reversed

    # Ship second order line on 2021-04-20 10:00
    And metasfresh has date and time 2021-04-20T10:00:00+01:00[Europe/Berlin]
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2_2               | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_s_2_2               | s_2_2      |

    # Validate both invoice candidates are ready
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | DeliveryDate |
      | ic_2_1                            | 10           | 10         | 10           | 2021-04-20   |
      | ic_2_2                            | 5            | 5          | 5            | 2021-04-20   |

    # Invoice both candidates together with IsDeliveryDateAsInvoiceDate
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IsDeliveryDateAsInvoiceDate |
      | ic_2_1                 | Y                           |
      | ic_2_2                 | Y                           |

    # Verify both candidates are on the same invoice
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_2_1                 | invoice_2    |
      | ic_2_2                 | invoice_2    |

    # Verify only one invoice was created for both candidates
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus |
      | invoice_2  | customer_1    | true      | CO        |

    # Verify invoice lines for both products
    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | il_2_1           | invoice_2    | p_1          | 10          |
      | il_2_2           | invoice_2    | p_2          | 5           |

