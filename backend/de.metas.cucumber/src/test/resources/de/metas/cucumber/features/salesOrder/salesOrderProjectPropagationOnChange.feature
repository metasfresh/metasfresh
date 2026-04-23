@from:cucumber
@ghActions:run_on_executor4
Feature: C_Project_ID propagates when changed on existing order line

  When C_Project_ID changes on a completed sales order line (e.g. inherited back from a dropship PO),
  the interceptor pushes the new project to the corresponding invoice candidates and shipment schedule.

  @from:cucumber
  @me03_28709
  Scenario: C_Project_ID update on order line propagates to invoice candidate and shipment schedule
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
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |
    And metasfresh contains C_Projects:
      | Identifier |
      | project_1  |

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
