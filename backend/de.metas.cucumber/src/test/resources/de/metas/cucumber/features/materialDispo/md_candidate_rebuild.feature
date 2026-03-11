@from:cucumber
@topic:materialdispo
@ghActions:run_on_executor6
Feature: MD_Candidate_Rebuild process
  As a user going live with metasfresh
  I want to rebuild all MD_Candidate data from source documents
  So that the ATP is clean and consistent
  See: https://github.com/metasfresh/me03/issues/28598

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2026-03-10T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   |
      | ps_1       | rebuild_pricing_system | rebuild_pricing_system  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | rebuild_so_pl      | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_so     | pl_so                     | rebuild_so_plv   | 2020-01-01 |
    And metasfresh contains C_BPartners:
      | Identifier  | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_1  | rebuild_customer | N            | Y              | ps_1                          |

  @from:cucumber
  @topic:materialdispo
  Scenario: Rebuild preserves SHIPMENT demand candidates from open sales orders
    And metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | rebuild_shipment_test_1 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_so                            | p_1                     | 10.0     | PCE               | Normal                        |

    # Create a sales order that generates shipment schedule + demand candidate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate     |
      | o_1        | true    | customer_1               | 2026-03-10  | 2026-03-15T10:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 50         |
    When the order identified by o_1 is completed

    # Wait for event-driven candidates to be created
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_demand   | DEMAND            | SHIPMENT                      | p_1                     | -50 |

    # Now run the rebuild process
    When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete="N"

    # Verify: demand candidate still exists with correct qty after rebuild
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_demand_r | DEMAND            | SHIPMENT                      | p_1                     | -50 |

  @from:cucumber
  @topic:materialdispo
  Scenario: Rebuild is idempotent — running twice gives same result
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | rebuild_idempotency_test_1 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_so                            | p_1                     | 10.0     | PCE               | Normal                        |

    # Create sales order → demand candidate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate     |
      | o_1        | true    | customer_1               | 2026-03-10  | 2026-03-15T10:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 30         |
    When the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Run rebuild FIRST time
    When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete="N"
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_demand_1 | DEMAND            | SHIPMENT                      | p_1                     | -30 |

    # Run rebuild SECOND time — idempotent, same result
    When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete="N"
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_demand_2 | DEMAND            | SHIPMENT                      | p_1                     | -30 |

  @from:cucumber
  @topic:materialdispo
  Scenario: Rebuild cleans orphan INVENTORY_UP candidates while preserving demand from open orders
    And metasfresh contains M_Products:
      | Identifier | Name                  |
      | p_1        | rebuild_orphan_test_1 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_so                            | p_1                     | 10.0     | PCE               | Normal                        |

    # Pre-populate: an INVENTORY_UP candidate that represents a historical adjustment (no source doc)
    Given metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise |
      | c_inv      | INVENTORY_UP      |                               | p_1                     | 2026-03-01T10:00:00.00Z | 200 | 200                    |

    # Also create a real sales order → demand candidate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate     |
      | o_1        | true    | customer_1               | 2026-03-10  | 2026-03-15T10:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 25         |
    When the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Confirm both candidates exist before rebuild
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_inv_pre  | INVENTORY_UP      |                               | p_1                     | 200 |
      | c_dem_pre  | DEMAND            | SHIPMENT                      | p_1                     | -25 |

    # Run rebuild — should delete INVENTORY_UP (no source doc) but keep SHIPMENT DEMAND
    When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete="N"

    # Verify: demand candidate preserved, INVENTORY_UP gone
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_dem_post | DEMAND            | SHIPMENT                      | p_1                     | -25 |
    And after not more than 60s, metasfresh has no MD_Candidate for identifier c_inv

  @from:cucumber
  @topic:materialdispo
  Scenario: Rebuild creates missing SHIPMENT demand candidates from open shipment schedules (INSERT path)
    And metasfresh contains M_Products:
      | Identifier | Name                     |
      | p_1        | rebuild_insert_test_1    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_so                            | p_1                     | 10.0     | PCE               | Normal                        |

    # Create a sales order → shipment schedule + demand candidate via events
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate     |
      | o_1        | true    | customer_1               | 2026-03-10  | 2026-03-15T10:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 40         |
    When the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Confirm candidate exists
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_demand   | DEMAND            | SHIPMENT                      | p_1                     | -40 |

    # Now delete all MD_Candidates for this product — simulates missing candidates
    When all MD_Candidates for product "p_1" are deleted

    # Run rebuild — should recreate the demand candidate from the open shipment schedule
    When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete="N"

    # Verify: demand candidate recreated with correct qty
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | Qty |
      | c_rebuilt  | DEMAND            | SHIPMENT                      | p_1                     | -40 |
