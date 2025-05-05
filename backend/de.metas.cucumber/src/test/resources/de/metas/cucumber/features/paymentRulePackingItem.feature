@from:cucumber
@ghActions:run_on_executor6
Feature: Validate that PaymentRule is correctly set on C_Order and that it correctly propagates on C_InvoiceCandidates
  (default payment rule is not propagated on C_InvoiceCandidates in this case, the payment rule set on C_Order is taken into consideration)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-22T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config String value P for sys config de.metas.invoice.C_Invoice_PaymentRule
    And assert defaultValue is P for tableName C_Invoice_Candidate and columnName PaymentRule

  @from:cucumber
  Scenario: Payment rule 'Cash' (set on C_Order) correctly propagates to Invoice Candidate for packing items (making sure, the default payment rule is set to OnCredit)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.PaymentRule | DateOrdered |
      | o_1        | true    | 2156425                  | B               | 2022-03-23  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 10         |
    And update order
      | C_Order_ID.Identifier | OPT.PaymentRule |
      | o_1                   | B               |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | ol_1                      | 3010001                     |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-03-23      | 2001343                 | 0            | 1          | 0           | 1     | 0        | EUR          | true      |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 10                        |

    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | s_s_1                            | D                 | Y                  |

    And after not more than 60s locate invoice candidates of order identified by o_1
      | C_Invoice_Candidate_ID.Identifier | M_Product_ID |
      | invoice_candidate_1               | 2005577      |
      | invoice_candidate_2               | 2001343      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
      | invoice_candidate_2               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | QtyToInvoice | OPT.PaymentRule |
      | invoice_candidate_1               | o_1                       | 10           | B               |
      | invoice_candidate_2               | o_1                       | 1            | B               |
