@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@ghActions:run_on_executor3
Feature: Order promised date propagates to shipment movement date and invoice date

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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised |
      | so_1       | true    | customer_1    | 2021-04-17  | 2021-04-25   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | DatePromised |
      | sol_1      | so_1       | p_1          | 10         | 2021-04-25   |
    And the order identified by so_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | s_s_1      | sol_1                     | N             | 10           |

    And metasfresh has date and time 2021-04-20T08:00:00+01:00[Europe/Berlin]

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                 | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And validate the created shipments
      | M_InOut_ID.Identifier | MovementDate |
      | s_1                   | 2021-04-25   |

    # No queue-drain step: the drain checks only the queue's ready-message count and is blind to
    # in-flight (unacked) cascade events, so it reports "empty" prematurely. The poll below waits
    # for the committed QtyToInvoice, which only settles after the async recompute cascade completes;
    # the `validate invoice candidate` step that follows further confirms the candidate is settled.
    And after not more than 300s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | sol_1                     | 10           |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | QtyOrdered | QtyDelivered | DeliveryDate |
      | ic_1                              | 10           | 10         | 10           | 2021-04-25   |

    And metasfresh has date and time 2021-04-30T08:00:00+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S30300_TC1
  Scenario: Order promised date propagates to shipment movement date and invoice date

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsDeliveryDateAsInvoiceDate |
      | ic_1                              | Y                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice_1    |
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus | DateInvoiced |
      | invoice_1  | customer_1    | true      | CO        | 2021-04-25   |

  @from:cucumber
  @Id:S30300_TC2
  Scenario: Invoice date option off dates the invoice to today, not to the order promised date

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsDeliveryDateAsInvoiceDate |
      | ic_1                              | N                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice_1    |
    And validate created invoices
      | Identifier | C_BPartner_ID | processed | DocStatus | DateInvoiced |
      | invoice_1  | customer_1    | true      | CO        | 2021-04-30   |
