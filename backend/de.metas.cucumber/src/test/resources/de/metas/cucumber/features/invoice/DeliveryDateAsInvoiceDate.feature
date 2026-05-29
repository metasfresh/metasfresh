@from:cucumber
@ghActions:run_on_executor3
Feature: Invoice Date can be taken from DeliveryDate

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
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

    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | customer_1 | N        | Y          | ps_1               |                      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | so_1       | true    | customer_1    | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | sol_1                     | N             |

    And metasfresh has date and time 2021-04-20T08:00:00+01:00[Europe/Berlin]

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    # Drain the material event queue so invoice-candidate async creation completes before the poll.
    # Background sets SKIP_WP_PROCESSOR_FOR_AUTOMATION=true, forcing async via RabbitMQ. Under CI load
    # this poll occasionally exhausts its 60 s budget, cascading to the step-def's 120 s recompute
    # fallback, which can in turn time out and abort the runner before any test completes
    # ("Tests run: 0").
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | sol_1                     | 10           |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | DeliveryDate |
      | ic_1                              | 10           | 10         | 10           | 2021-04-20   |

    And metasfresh has date and time 2021-04-30T08:00:00+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Invoice with DeliveryDateAsInvoiceDate = 'N'. InvoiceDate will be today.

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_1                   |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice_1    |
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus | DateInvoiced |
      | invoice_1  | customer_1    | true      | CO        | 2021-04-30   |

  @from:cucumber
  Scenario: Invoice with DeliveryDateAsInvoiceDate = 'Y'. InvoiceDate will be Delivery date.

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IsDeliveryDateAsInvoiceDate |
      | ic_1                   | Y                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice_1    |
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus | DateInvoiced |
      | invoice_1  | customer_1    | true      | CO        | 2021-04-20   |

  @from:cucumber
  Scenario: Invoice with DeliveryDateAsInvoiceDate = 'Y' and Invoice date override provided. InvoiceDate will be the delivery date.

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IsDeliveryDateAsInvoiceDate | DateInvoiced |
      | ic_1                   | Y                           | 2021-04-25   |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice_1    |
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus | DateInvoiced |
      | invoice_1  | customer_1    | true      | CO        | 2021-04-20   |

