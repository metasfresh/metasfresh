-- View MD_Stock_PerWeek_V (me03 25618 / F19100 — "Stock per week").
--
-- Derives, per product x warehouse x ISO calendar week (Monday-anchored), the material
-- outlook straight from the dispo engine's MD_Candidate timeline:
--   QtyExpectedShipments : reserved demand   (DEMAND / SHIPMENT,  ABS(Qty))
--   QtyExpectedReceipts  : purchased supply  (SUPPLY / PURCHASE,  Qty)
--   QtyATP               : projected stock   (latest STOCK candidate at/before week-end,
--                          summed across attribute/customer subgroups) — the authoritative
--                          available-to-promise number; it reflects ALL streams (shipment,
--                          purchase, production, distribution, forecast, inventory), so its
--                          row-to-row delta need NOT equal receipts - shipments.
--
-- Horizon: current week .. current+N, where N = SysConfig
--   'de.metas.material.stockperweek.HorizonWeeks' (default 12 => 13 rows).
-- Overdue activity (dated before the current week) is rolled into the current-week row
-- via GREATEST(week-of(DateProjected), current-week) so backlog is never hidden.
-- Only active, non-'simulated' candidates are considered.
--
-- Aggregation defaults (DESIGN.md §7 — confirm with customer at UAT):
--   * attributes : summed across StorageAttributesKey (latest STOCK per key, then SUM)
--   * customer   : the customer dimension (C_BPartner_Customer_ID) is summed in too, i.e.
--                  the overall projected stock is shown — no per-customer reserve carve-out.

-- No synthetic row_number() primary key: a row_number() OVER (...) window function forces the
-- planner to materialize and sort EVERY (product x warehouse x week) row before any outer
-- predicate (e.g. the zoom's M_Product_ID filter) can be applied — the filter cannot be pushed
-- down past the window, making a single-product zoom as expensive as browse-all. Dropping it
-- restores predicate push-down to the MD_Candidate scans. This is a read-only view-backed window.
CREATE OR REPLACE VIEW MD_Stock_PerWeek_V AS
WITH horizon AS (
  SELECT GREATEST(1, COALESCE(NULLIF(
           (SELECT Value FROM AD_SysConfig
             WHERE Name = 'de.metas.material.stockperweek.HorizonWeeks' AND IsActive = 'Y'
             ORDER BY AD_Client_ID DESC, AD_Org_ID DESC
             LIMIT 1), '')::int, 12)) AS weeks
),
-- distinct product/warehouse pairs that have any active, non-simulated candidate;
-- AD_Client_ID / AD_Org_ID are included so the security framework can filter by client.
pw AS (
  SELECT DISTINCT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.M_Warehouse_ID
    FROM MD_Candidate c
   WHERE c.IsActive = 'Y'
     AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
),
-- one row per (product, warehouse, week) across the configured horizon
weeks AS (
  SELECT pw.AD_Client_ID,
         pw.AD_Org_ID,
         pw.M_Product_ID,
         pw.M_Warehouse_ID,
         (date_trunc('week', current_date)::date + (g.w * 7)) AS WeekStartDate
    FROM pw
   CROSS JOIN horizon h
   CROSS JOIN generate_series(0, h.weeks) AS g(w)
)
SELECT
  (row_number() OVER (ORDER BY w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate))::int AS MD_Stock_PerWeek_V_ID,
  w.AD_Client_ID,
  w.AD_Org_ID,
  w.M_Product_ID,
  w.M_Warehouse_ID,
  w.WeekStartDate,
  -- expected shipments: DEMAND/SHIPMENT in this week (overdue rolled into current week)
  COALESCE(( SELECT SUM(ABS(c.Qty))
               FROM MD_Candidate c
              WHERE c.IsActive = 'Y'
                AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
                AND c.M_Product_ID  = w.M_Product_ID
                AND c.M_Warehouse_ID = w.M_Warehouse_ID
                AND c.MD_Candidate_Type = 'DEMAND'
                AND c.MD_Candidate_BusinessCase = 'SHIPMENT'
                AND GREATEST(date_trunc('week', c.DateProjected)::date,
                             date_trunc('week', current_date)::date) = w.WeekStartDate ), 0)
    AS QtyExpectedShipments,
  -- expected receipts: SUPPLY/PURCHASE in this week (overdue rolled into current week)
  COALESCE(( SELECT SUM(c.Qty)
               FROM MD_Candidate c
              WHERE c.IsActive = 'Y'
                AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
                AND c.M_Product_ID  = w.M_Product_ID
                AND c.M_Warehouse_ID = w.M_Warehouse_ID
                AND c.MD_Candidate_Type = 'SUPPLY'
                AND c.MD_Candidate_BusinessCase = 'PURCHASE'
                AND GREATEST(date_trunc('week', c.DateProjected)::date,
                             date_trunc('week', current_date)::date) = w.WeekStartDate ), 0)
    AS QtyExpectedReceipts,
  -- ATP at week-end: latest STOCK candidate at/before the end of this week, per
  -- attribute/customer subgroup, then summed.
  COALESCE(( SELECT SUM(latest.Qty)
               FROM ( SELECT DISTINCT ON (c.StorageAttributesKey, c.C_BPartner_Customer_ID) c.Qty
                        FROM MD_Candidate c
                       WHERE c.IsActive = 'Y'
                         AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
                         AND c.M_Product_ID  = w.M_Product_ID
                         AND c.M_Warehouse_ID = w.M_Warehouse_ID
                         AND c.MD_Candidate_Type = 'STOCK'
                         AND c.DateProjected < (w.WeekStartDate + 7)::timestamptz
                       ORDER BY c.StorageAttributesKey, c.C_BPartner_Customer_ID,
                                c.DateProjected DESC, c.SeqNo DESC
                    ) latest ), 0)
    AS QtyATP
FROM weeks w;