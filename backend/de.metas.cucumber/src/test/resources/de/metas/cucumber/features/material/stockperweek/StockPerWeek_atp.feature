@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19100
@ghActions:run_on_executor6
Feature: MD_Stock_PerWeek_V shows cumulative projected stock (QtyATP) and rolls overdue demand into the current week

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-03T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | M_Product_Category_ID    | Name     | Value    |
      | standard_category_S25618 | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID  |
      | wh_S25618_atp   |

  @Id:S25618_20
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F19100
  Scenario: QtyATP at week-end reflects cumulative projected stock across all candidate streams

    # This test seeds stock (INVENTORY_UP, ATP=100) + demand (DEMAND/SHIPMENT, ATP=90) +
    # supply (SUPPLY/PURCHASE, ATP=98) in consecutive weeks and asserts QtyATP in each week.
    # The seeded STOCK candidates (created by the step as paired records) carry the ATP values
    # that the dispo engine would compute; the view picks the latest STOCK candidate per
    # (storageAttributesKey, bpartner_customer_id) group whose DateProjected < weekStart+7.
    Given metasfresh contains M_Products:
      | Identifier          | M_Product_Category_ID    | C_UOM_ID.X12DE355 |
      | product_S25618_atp1 | standard_category_S25618 | PCE               |

    # Week 0 (2026-06-01): seed stock = 100 units on hand.
    # The INVENTORY_UP step creates a paired STOCK candidate with Qty=100 dated 2026-06-01.
    And metasfresh initially has this MD_Candidate data
      | Identifier         | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID        | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | inv_S25618_atp1    | INVENTORY_UP      |                           | product_S25618_atp1 | 2026-06-01T21:00:00Z | 100 | 100 | wh_S25618_atp  |

    # Week +1 (2026-06-08): one DEMAND/SHIPMENT of 10, leaving ATP=90.
    And metasfresh initially has this MD_Candidate data
      | Identifier         | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID        | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | demand_S25618_atp1 | DEMAND            | SHIPMENT                  | product_S25618_atp1 | 2026-06-08T21:00:00Z | 10  | 90  | wh_S25618_atp  |

    # Week +2 (2026-06-15): one SUPPLY/PURCHASE of 8, leaving ATP=98.
    And metasfresh initially has this MD_Candidate data
      | Identifier         | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID        | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | supply_S25618_atp1 | SUPPLY            | PURCHASE                  | product_S25618_atp1 | 2026-06-15T21:00:00Z | 8   | 98  | wh_S25618_atp  |

    # Week 0 row: QtyATP = 100 (the STOCK candidate from the INVENTORY_UP, DateProjected 2026-06-01
    # < 2026-06-08, so visible in week 0's window [before 2026-06-08])
    Then after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekStartDate | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_atp1 | wh_S25618_atp  | 2026-06-01    | 0                    | 0                   | 100    |

    # Week +1 row: latest STOCK before 2026-06-15 is the one at 2026-06-08 with Qty=90.
    And after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekStartDate | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_atp1 | wh_S25618_atp  | 2026-06-08    | 10                   | 0                   | 90     |

    # Week +2 row: latest STOCK before 2026-06-22 is the one at 2026-06-15 with Qty=98.
    And after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekStartDate | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_atp1 | wh_S25618_atp  | 2026-06-15    | 0                    | 8                   | 98     |

  @Id:S25618_30
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F19100
  Scenario: Overdue DEMAND/SHIPMENT dated before the current week is rolled into the current week's QtyExpectedShipments

    # The view applies GREATEST(date_trunc('week', DateProjected), date_trunc('week', now()))
    # so any DEMAND/SHIPMENT whose DateProjected falls in a past week still counts in the
    # current-week row's QtyExpectedShipments — it is not silently dropped.
    Given metasfresh contains M_Products:
      | Identifier          | M_Product_Category_ID    | C_UOM_ID.X12DE355 |
      | product_S25618_atp2 | standard_category_S25618 | PCE               |

    # This DEMAND is dated in a past week (2026-05-18 = week of 2026-05-18) but must appear
    # in the current week (2026-06-01) row's QtyExpectedShipments via overdue rolling.
    And metasfresh initially has this MD_Candidate data
      | Identifier          | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID        | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | overdue_S25618_atp2 | DEMAND            | SHIPMENT                  | product_S25618_atp2 | 2026-05-18T21:00:00Z | 7   | -7  | wh_S25618_atp  |

    # The current-week row (2026-06-01) must show QtyExpectedShipments=7 for the overdue demand.
    # QtyATP=-7: the STOCK candidate paired with this DEMAND is dated 2026-05-18T21:00:00Z with
    # Qty=ATP=-7. That date is before 2026-06-08T00:00:00Z (week-end of 2026-06-01 week), so
    # it is the latest STOCK for this product/warehouse → QtyATP=-7.
    Then after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekStartDate | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_atp2 | wh_S25618_atp  | 2026-06-01    | 7                    | 0                   | -7     |
