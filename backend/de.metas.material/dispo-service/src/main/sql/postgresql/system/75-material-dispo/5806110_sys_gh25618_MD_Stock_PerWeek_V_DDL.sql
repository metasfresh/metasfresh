-- me03 25618 / F19100 — Stock per week.
-- Source DDL: backend/de.metas.material/dispo-service/src/main/sql/postgresql/ddl/de_metas_material/MD_Stock_PerWeek_V.sql
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_MigrationScript sequence: 5806110 (filename prefix)
--
-- Per-product weekly stock view over MD_Candidate. Columns:
--   AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID, WeekStartDate,
--   QtyExpectedShipments, QtyExpectedReceipts, QtyATP
-- Full semantics documented in the ddl-mirror file above.
--
-- No synthetic row_number() primary key: a row_number() OVER (...) window function forces the
-- planner to materialize and sort EVERY (product x warehouse x week) row before any outer
-- predicate (e.g. the zoom's M_Product_ID = @id@ filter) can be applied — the filter cannot be
-- pushed down past the window. That made a single-product zoom as expensive as browse-all
-- (~1.05M planner cost on the seed DB). Dropping the synthetic key restores predicate
-- push-down: the WHERE M_Product_ID = ... filter reaches the MD_Candidate scans directly.
-- This is a read-only view-backed window, so it works keyless.

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;

CREATE OR REPLACE VIEW MD_Stock_PerWeek_V$new AS
WITH horizon AS (
  SELECT GREATEST(1, COALESCE(NULLIF(
           (SELECT Value FROM AD_SysConfig
             WHERE Name = 'de.metas.material.stockperweek.HorizonWeeks' AND IsActive = 'Y'
             ORDER BY AD_Client_ID DESC, AD_Org_ID DESC
             LIMIT 1), '')::int, 12)) AS weeks
),
pw AS (
  SELECT DISTINCT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.M_Warehouse_ID
    FROM MD_Candidate c
   WHERE c.IsActive = 'Y'
     AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
),
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
  w.AD_Client_ID,
  w.AD_Org_ID,
  w.M_Product_ID,
  w.M_Warehouse_ID,
  w.WeekStartDate,
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

SELECT db_alter_view(
    'MD_Stock_PerWeek_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('MD_Stock_PerWeek_V$new'))
);

DROP VIEW IF EXISTS MD_Stock_PerWeek_V$new;