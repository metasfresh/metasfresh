-- me03 30457 — add QtyATPBegin (projected ATP as-of the START of each week) to MD_Stock_PerWeek_V.
-- Source DDL: backend/de.metas.material/dispo-service/src/main/sql/postgresql/ddl/de_metas_material/MD_Stock_PerWeek_V.sql
-- IDs allocated from idserver.metas.de on 2026-06-17:
--   AD_MigrationScript sequence: 5808360 (filename prefix)
--
-- Adds a new measure column QtyATPBegin alongside the existing QtyATP:
--   QtyATP      = projected ATP as-of week END   (latest STOCK candidate with DateProjected < WeekStartDate + 7)
--   QtyATPBegin = projected ATP as-of week START (latest STOCK candidate with DateProjected < WeekStartDate)
-- Both reuse the same atp_steps half-open-interval machinery (collapse-per-DateProjected + LEAD);
-- the only difference is the week boundary (WeekStartDate vs WeekStartDate + 7).
-- By construction QtyATPBegin(W) == QtyATP(W-1) (a week's start ATP equals the prior week's end ATP).
--
-- QtyATPBegin is placed BEFORE QtyExpectedShipments so the view's natural column order matches the
-- UI order: ATP Beginn | Erw. Lieferungen | Erw. Wareneingänge | ATP Ende.
-- The hash PK and all existing columns/values are UNCHANGED.
-- (The AD_Column / AD_Field / labels for QtyATPBegin are added in a separate AD-metadata migration.)

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;

CREATE OR REPLACE VIEW MD_Stock_PerWeek_V$new AS
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

SELECT db_alter_view(
    'MD_Stock_PerWeek_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('MD_Stock_PerWeek_V$new'))
);

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;
