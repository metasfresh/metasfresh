@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020_C_Project_propagation_through_Purchase_Sales_flow
@ghActions:run_on_executor4
Feature: C_Project_ID propagates when changed on existing order line

  When C_Project_ID changes on a completed sales order line (e.g. inherited back from a dropship PO),
  the interceptor pushes the new project to the corresponding invoice candidates and shipment schedule.

  The push reaches only invoice candidates that exist when the order line is saved. A candidate that is
  created at the same time (async) can miss it. Therefore every recompute of an unprocessed invoice candidate
  also copies the order line's project. A processed (invoiced) candidate keeps its project.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | customer_1 | Y          | ps_1               | D           |
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |
    And metasfresh contains C_Projects:
      | Identifier |
      | project_1  |

  @from:cucumber
  Scenario: C_Project_ID update on order line propagates to invoice candidate and shipment schedule
    # Create and complete SO without project on order line
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed

    # Wait for IC and shipment schedule to be created
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | sol_1          |

    # Verify they have no project yet
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | sol_1          |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | C_OrderLine_ID |
      | ss_1                  | sol_1          |

    # Now simulate project being set on order line (as would happen in dropship scenario)
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Project_ID.Identifier |
      | sol_1                     | project_1                   |

    # Verify project propagated to invoice candidate
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | project_1    |

    # Verify project propagated to shipment schedule
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | C_Project_ID |
      | ss_1                  | project_1    |

  @from:cucumber
  Scenario: invoice candidate that missed the push gets the order line's project on its next recompute
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | sol_1          |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_1                   |

    # The order line gets its project, but the push to the existing candidate is missed (as in the async race)
    And update C_OrderLine.C_Project_ID in the DB, bypassing model interceptors:
      | C_OrderLine_ID | C_Project_ID |
      | sol_1          | project_1    |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | null         |

    # Shipping the goods recomputes the candidate
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    Then after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID | QtyToInvoice |
      | ic_1                   | sol_1          | 10           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | project_1    |

    # The invoice line takes the project from the candidate
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | C_Project_ID |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      | project_1    |

  @from:cucumber
  Scenario: recompute clears the invoice candidate's project when the order line's project was removed and the push was missed
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID |
      | sol_1      | so_1       | p_1          | 10         | project_1    |
    And the order identified by so_1 is completed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | sol_1          |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | project_1    |

    When update C_OrderLine.C_Project_ID in the DB, bypassing model interceptors:
      | C_OrderLine_ID | C_Project_ID |
      | sol_1          | null         |
    And after not more than 60s, C_Invoice_Candidates are invalidated and recomputed:
      | C_Invoice_Candidate_ID |
      | ic_1                   |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | null         |

  @from:cucumber
  Scenario: recompute does not change the project of an invoiced (processed) invoice candidate
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | p_1          | 10         |
    And the order identified by so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID | QtyToInvoice |
      | ic_1                   | sol_1          | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |

    When update C_OrderLine.C_Project_ID in the DB, bypassing model interceptors:
      | C_OrderLine_ID | C_Project_ID |
      | sol_1          | project_1    |
    And after not more than 60s, C_Invoice_Candidates are invalidated and recomputed:
      | C_Invoice_Candidate_ID |
      | ic_1                   |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | Processed | C_Project_ID |
      | ic_1                   | true      | null         |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Project_ID |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | null         |
