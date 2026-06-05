@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19100
@ghActions:run_on_executor6
Feature: MD_Stock_PerWeek_V aggregates DEMAND/SHIPMENT and SUPPLY/PURCHASE candidates into weekly buckets

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
      | wh_S25618_str   |

  @Id:S25618_10
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F19100
  Scenario: DEMAND/SHIPMENT in week +2 and SUPPLY/PURCHASE in week +1 appear in the correct weekly buckets

    Given metasfresh contains M_Products:
      | Identifier          | M_Product_Category_ID    | C_UOM_ID.X12DE355 |
      | product_S25618_str1 | standard_category_S25618 | PCE               |

    # Seed candidates directly — this stands in for the full SO→dispo-engine flow
    # because the dispo engine is async and the test purpose is the view's bucketing logic.
    # In production these rows are created by the MaterialDispoService when a sales order
    # and a purchase order are completed.
    # Dates are relative to the current ISO week (WeekOffset=0 → this Monday) so the test
    # remains stable on any run date.
    And metasfresh initially has this MD_Candidate data relative to current week
      | Identifier      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID        | WeekOffset | DayWithinWeek | Qty | ATP | M_Warehouse_ID |
      | demand_S25618_1 | DEMAND            | SHIPMENT                  | product_S25618_str1 | 2          | 1             | 5   | 3   | wh_S25618_str  |
      | supply_S25618_1 | SUPPLY            | PURCHASE                  | product_S25618_str1 | 1          | 1             | 8   | 8   | wh_S25618_str  |

    # Week +1: QtyExpectedShipments=0, QtyExpectedReceipts=8
    # QtyATP=8: the STOCK candidate paired with the SUPPLY (Qty=8) is dated in week+1,
    # which is before the week+2 start, so it is the latest STOCK and drives ATP.
    Then after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekOffset | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_str1 | wh_S25618_str  | 1          | 0                    | 8                   | 8      |

    # Week +2: QtyExpectedShipments=5, QtyExpectedReceipts=0
    # QtyATP=3: the latest STOCK before week+3 is the DEMAND-paired STOCK in week+2. It carries
    # the cumulative projected balance 8 - 5 = 3 (the +8 receipt from week+1 minus the 5 shipment
    # in week+2) — ATP is the running stock balance, NOT the shipment qty in isolation.
    And after not more than 10s, MD_Stock_PerWeek_V contains:
      | M_Product_ID        | M_Warehouse_ID | WeekOffset | QtyExpectedShipments | QtyExpectedReceipts | QtyATP |
      | product_S25618_str1 | wh_S25618_str  | 2          | 5                    | 0                   | 3      |
