-- View MD_Stock_PerWeek_V (me03 25618 / F19100 — "Stock per week").
--
-- Derives, per product x warehouse x ISO calendar week (Monday-anchored), the material
-- outlook straight from the dispo engine's MD_Candidate timeline:
--   QtyATPBegin          : projected stock as-of the START of the week (the Monday) —
--                          latest STOCK candidate with DateProjected < WeekStartDate,
--                          summed across attribute/customer subgroups. By construction,
--                          QtyATPBegin(W) == QtyATP(W-1): the ATP at a week's start equals
--                          the ATP at the prior week's end (same Monday boundary).
--   QtyExpectedShipments : reserved demand   (DEMAND / SHIPMENT,  ABS(Qty))
--   QtyExpectedReceipts  : purchased supply  (SUPPLY / PURCHASE,  Qty)
--   QtyATP               : projected stock as-of the END of the week — latest STOCK
--                          candidate at/before week-end (DateProjected < WeekStartDate + 7),
--                          summed across attribute/customer subgroups — the authoritative
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
--
-- Single-scan rewrite (me03 30457):
--   Replaces 3 correlated per-row subqueries (each scanning MD_Candidate) with pre-aggregated
--   CTEs that scan MD_Candidate essentially once per measure:
--
--   Shipments / Receipts:
--     One grouped scan each, bucketed by the overdue-rollup week
--     GREATEST(date_trunc('week',DateProjected), date_trunc('week',current_date)).
--     Joined to the week skeleton by (product, warehouse, week).
--
--   QtyATP / QtyATPBegin single-pass technique:
--     1. atp_collapsed: per (pw, subgroup, DateProjected) keep the Qty of the
--        max-SeqNo candidate (DISTINCT ON ... ORDER BY SeqNo DESC). This makes the
--        carry-forward exact under ties.
--     2. atp_steps: LEAD(DateProjected) over each subgroup gives a half-open validity
--        interval [DateProjected, next_DateProjected). A step owns week W (end) iff
--            DateProjected < W+7  AND  (next IS NULL OR next >= W+7);
--        it owns week W (begin) iff
--            DateProjected < W    AND  (next IS NULL OR next >= W).
--        The inequalities are the same timestamp comparisons as the original; the only
--        difference between end-of-week (QtyATP) and start-of-week (QtyATPBegin) is the
--        boundary: WeekStartDate + 7 (next Monday) vs WeekStartDate (this Monday).
--     3. Join steps to the week skeleton on that interval and SUM Qty per (pw, week).
--
--   Weeks come from generate_series LATERAL-joined to the per-pw aggregates (not a
--   CROSS JOIN that would explode all candidates).
--
-- Push-down-friendly synthetic primary key: a deterministic hash of (M_Product_ID, M_Warehouse_ID,
-- WeekStartDate) encoded as a 32-bit integer via MD5 truncation. This is a per-row scalar
-- expression — unlike row_number() OVER (...), it does NOT force the planner to materialise every
-- row before outer predicates are applied, so a single-product zoom (WHERE M_Product_ID = @x@)
-- is as cheap as ~94k planner cost (vs. ~1.05M with the row_number regression).
-- Canonical metasfresh-db pattern: ABS(('x'||SUBSTR(MD5(...),1,10))::bit(32)::int).
-- 10 hex digits → ~4 billion values; collision probability negligible below ~77k distinct combos.
CREATE OR REPLACE VIEW MD_Stock_PerWeek_V AS
WITH horizon AS (
  SELECT GREATEST(1, COALESCE(NULLIF(
           (SELECT Value FROM AD_SysConfig
             WHERE Name = 'de.metas.material.stockperweek.HorizonWeeks' AND IsActive = 'Y'
             ORDER BY AD_Client_ID DESC, AD_Org_ID DESC
             LIMIT 1), '')::int, 12)) AS weeks
),
-- base universe of relevant candidates (one scan, materialised conceptually)
base AS (
  SELECT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.M_Warehouse_ID,
         c.MD_Candidate_Type, c.MD_Candidate_BusinessCase,
         c.Qty, c.DateProjected, c.SeqNo,
         c.StorageAttributesKey, c.C_BPartner_Customer_ID
    FROM MD_Candidate c
   WHERE c.IsActive = 'Y'
     AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
),
-- distinct product/warehouse pairs (the grain), client/org carried for security filtering
pw AS (
  SELECT DISTINCT AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID
    FROM base
),
-- one row per (product, warehouse, week) across the configured horizon (LATERAL series)
weeks AS (
  SELECT pw.AD_Client_ID, pw.AD_Org_ID, pw.M_Product_ID, pw.M_Warehouse_ID,
         (date_trunc('week', current_date)::date + (g.w * 7)) AS WeekStartDate
    FROM pw
   CROSS JOIN horizon h
   CROSS JOIN LATERAL generate_series(0, h.weeks) AS g(w)
),
-- ===== Shipments: DEMAND/SHIPMENT, ABS(Qty), overdue rolled into current week =====
ship AS (
  SELECT b.M_Product_ID, b.M_Warehouse_ID,
         GREATEST(date_trunc('week', b.DateProjected)::date,
                  date_trunc('week', current_date)::date) AS WeekStartDate,
         SUM(ABS(b.Qty)) AS qty
    FROM base b
   WHERE b.MD_Candidate_Type = 'DEMAND'
     AND b.MD_Candidate_BusinessCase = 'SHIPMENT'
   GROUP BY b.M_Product_ID, b.M_Warehouse_ID,
            GREATEST(date_trunc('week', b.DateProjected)::date,
                     date_trunc('week', current_date)::date)
),
-- ===== Receipts: SUPPLY/PURCHASE, Qty, overdue rolled into current week =====
recv AS (
  SELECT b.M_Product_ID, b.M_Warehouse_ID,
         GREATEST(date_trunc('week', b.DateProjected)::date,
                  date_trunc('week', current_date)::date) AS WeekStartDate,
         SUM(b.Qty) AS qty
    FROM base b
   WHERE b.MD_Candidate_Type = 'SUPPLY'
     AND b.MD_Candidate_BusinessCase = 'PURCHASE'
   GROUP BY b.M_Product_ID, b.M_Warehouse_ID,
            GREATEST(date_trunc('week', b.DateProjected)::date,
                     date_trunc('week', current_date)::date)
),
-- ===== ATP step series =====
-- 1) collapse to one row per (pw, subgroup, DateProjected): keep Qty of the max-SeqNo row.
atp_collapsed AS (
  SELECT DISTINCT ON (b.M_Product_ID, b.M_Warehouse_ID,
                      b.StorageAttributesKey, b.C_BPartner_Customer_ID, b.DateProjected)
         b.M_Product_ID, b.M_Warehouse_ID,
         b.StorageAttributesKey, b.C_BPartner_Customer_ID,
         b.DateProjected, b.Qty
    FROM base b
   WHERE b.MD_Candidate_Type = 'STOCK'
   ORDER BY b.M_Product_ID, b.M_Warehouse_ID,
            b.StorageAttributesKey, b.C_BPartner_Customer_ID, b.DateProjected,
            b.SeqNo DESC
),
-- 2) half-open validity interval [DateProjected, next_DateProjected) per subgroup
atp_steps AS (
  SELECT M_Product_ID, M_Warehouse_ID, DateProjected, Qty,
         LEAD(DateProjected) OVER (
           PARTITION BY M_Product_ID, M_Warehouse_ID,
                        StorageAttributesKey, C_BPartner_Customer_ID
           ORDER BY DateProjected
         ) AS next_dp
    FROM atp_collapsed
),
-- 3a) ATP as-of week END: step owns week W iff DateProjected < W+7 AND (next_dp IS NULL OR next_dp >= W+7)
atp AS (
  SELECT w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate,
         SUM(s.Qty) AS qty
    FROM weeks w
    JOIN atp_steps s
      ON s.M_Product_ID  = w.M_Product_ID
     AND s.M_Warehouse_ID = w.M_Warehouse_ID
     AND s.DateProjected < (w.WeekStartDate + 7)::timestamptz
     AND (s.next_dp IS NULL OR s.next_dp >= (w.WeekStartDate + 7)::timestamptz)
   GROUP BY w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate
),
-- 3b) ATP as-of week START (the Monday): same technique, boundary at WeekStartDate (not +7).
--     step owns week W iff DateProjected < W AND (next_dp IS NULL OR next_dp >= W).
--     By construction this equals the prior week's QtyATP (its week-end ATP).
atp_begin AS (
  SELECT w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate,
         SUM(s.Qty) AS qty
    FROM weeks w
    JOIN atp_steps s
      ON s.M_Product_ID  = w.M_Product_ID
     AND s.M_Warehouse_ID = w.M_Warehouse_ID
     AND s.DateProjected < (w.WeekStartDate)::timestamptz
     AND (s.next_dp IS NULL OR s.next_dp >= (w.WeekStartDate)::timestamptz)
   GROUP BY w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate
)
SELECT
  ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                               w.M_Product_ID::text,
                               w.M_Warehouse_ID::text,
                               w.WeekStartDate::text)), 1, 10))::bit(32)::int))
           AS MD_Stock_PerWeek_V_ID,
  w.AD_Client_ID,
  w.AD_Org_ID,
  w.M_Product_ID,
  w.M_Warehouse_ID,
  w.WeekStartDate,
  COALESCE(atp_begin.qty, 0) AS QtyATPBegin,
  COALESCE(ship.qty, 0) AS QtyExpectedShipments,
  COALESCE(recv.qty, 0) AS QtyExpectedReceipts,
  COALESCE(atp.qty,  0) AS QtyATP
FROM weeks w
LEFT JOIN ship ON ship.M_Product_ID = w.M_Product_ID
              AND ship.M_Warehouse_ID = w.M_Warehouse_ID
              AND ship.WeekStartDate = w.WeekStartDate
LEFT JOIN recv ON recv.M_Product_ID = w.M_Product_ID
              AND recv.M_Warehouse_ID = w.M_Warehouse_ID
              AND recv.WeekStartDate = w.WeekStartDate
LEFT JOIN atp  ON atp.M_Product_ID = w.M_Product_ID
              AND atp.M_Warehouse_ID = w.M_Warehouse_ID
              AND atp.WeekStartDate = w.WeekStartDate
LEFT JOIN atp_begin ON atp_begin.M_Product_ID = w.M_Product_ID
              AND atp_begin.M_Warehouse_ID = w.M_Warehouse_ID
              AND atp_begin.WeekStartDate = w.WeekStartDate;
