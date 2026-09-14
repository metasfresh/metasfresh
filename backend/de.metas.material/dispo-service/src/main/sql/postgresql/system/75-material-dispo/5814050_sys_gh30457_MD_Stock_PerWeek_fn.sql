-- Source DDL (function): backend/de.metas.material/dispo-service/src/main/sql/postgresql/ddl/de_metas_material/MD_Stock_PerWeek_fn.sql
--
-- gh#30457 — fast filtered "Bestand pro Woche" / stock per week.
--
-- MD_Stock_PerWeek_fn(product, warehouse): parameterized companion of MD_Stock_PerWeek_V that
-- pushes the product/warehouse filter INTO the base MD_Candidate scan (using the partial index
-- md_candidate_perweek_pw_idx, see 5814060) instead of filtering the fully-materialized view.
-- Output is byte-identical to the view for the same filter.

CREATE OR REPLACE FUNCTION MD_Stock_PerWeek_fn(
    p_product_id   numeric DEFAULT NULL,   -- NULL = no product filter (degrades to full view)
    p_warehouse_id numeric DEFAULT NULL    -- NULL = all warehouses for the product
)
RETURNS TABLE (
    MD_Stock_PerWeek_V_ID  integer,
    AD_Client_ID           numeric,
    AD_Org_ID              numeric,
    M_Product_ID           numeric,
    M_Warehouse_ID         numeric,
    WeekStartDate          date,
    QtyATPBegin            numeric,
    QtyExpectedShipments   numeric,
    QtyExpectedReceipts    numeric,
    QtyATP                 numeric
)
LANGUAGE sql
STABLE
AS $func$
WITH horizon AS (
  SELECT GREATEST(1, COALESCE(NULLIF(
           (SELECT Value FROM AD_SysConfig
             WHERE Name = 'de.metas.material.stockperweek.HorizonWeeks' AND IsActive = 'Y'
             ORDER BY AD_Client_ID DESC, AD_Org_ID DESC
             LIMIT 1), '')::int, 12)) AS weeks
),
base AS (
  SELECT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.M_Warehouse_ID,
         c.MD_Candidate_Type, c.MD_Candidate_BusinessCase,
         c.Qty, c.DateProjected, c.SeqNo,
         c.StorageAttributesKey, c.C_BPartner_Customer_ID
    FROM MD_Candidate c
   WHERE c.IsActive = 'Y'
     AND c.MD_Candidate_Status IS DISTINCT FROM 'simulated'
     AND (p_product_id   IS NULL OR c.M_Product_ID   = p_product_id)     -- << pushed-down filter
     AND (p_warehouse_id IS NULL OR c.M_Warehouse_ID = p_warehouse_id)   -- << pushed-down filter
),
pw AS (
  SELECT DISTINCT AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID FROM base
),
weeks AS (
  SELECT pw.AD_Client_ID, pw.AD_Org_ID, pw.M_Product_ID, pw.M_Warehouse_ID,
         (date_trunc('week', current_date)::date + (g.w * 7)) AS WeekStartDate
    FROM pw
   CROSS JOIN horizon h
   CROSS JOIN LATERAL generate_series(0, h.weeks) AS g(w)
),
ship AS (
  SELECT b.M_Product_ID, b.M_Warehouse_ID,
         GREATEST(date_trunc('week', b.DateProjected)::date,
                  date_trunc('week', current_date)::date) AS WeekStartDate,
         SUM(ABS(b.Qty)) AS qty
    FROM base b
   WHERE b.MD_Candidate_Type = 'DEMAND' AND b.MD_Candidate_BusinessCase = 'SHIPMENT'
   GROUP BY b.M_Product_ID, b.M_Warehouse_ID,
            GREATEST(date_trunc('week', b.DateProjected)::date, date_trunc('week', current_date)::date)
),
recv AS (
  SELECT b.M_Product_ID, b.M_Warehouse_ID,
         GREATEST(date_trunc('week', b.DateProjected)::date,
                  date_trunc('week', current_date)::date) AS WeekStartDate,
         SUM(b.Qty) AS qty
    FROM base b
   WHERE b.MD_Candidate_Type = 'SUPPLY' AND b.MD_Candidate_BusinessCase = 'PURCHASE'
   GROUP BY b.M_Product_ID, b.M_Warehouse_ID,
            GREATEST(date_trunc('week', b.DateProjected)::date, date_trunc('week', current_date)::date)
),
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
atp_steps AS (
  SELECT M_Product_ID, M_Warehouse_ID, DateProjected, Qty,
         LEAD(DateProjected) OVER (
           PARTITION BY M_Product_ID, M_Warehouse_ID,
                        StorageAttributesKey, C_BPartner_Customer_ID
           ORDER BY DateProjected
         ) AS next_dp
    FROM atp_collapsed
),
atp AS (
  SELECT w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate, SUM(s.Qty) AS qty
    FROM weeks w
    JOIN atp_steps s
      ON s.M_Product_ID = w.M_Product_ID AND s.M_Warehouse_ID = w.M_Warehouse_ID
     AND s.DateProjected < (w.WeekStartDate + 7)::timestamptz
     AND (s.next_dp IS NULL OR s.next_dp >= (w.WeekStartDate + 7)::timestamptz)
   GROUP BY w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate
),
atp_begin AS (
  SELECT w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate, SUM(s.Qty) AS qty
    FROM weeks w
    JOIN atp_steps s
      ON s.M_Product_ID = w.M_Product_ID AND s.M_Warehouse_ID = w.M_Warehouse_ID
     AND s.DateProjected < (w.WeekStartDate)::timestamptz
     AND (s.next_dp IS NULL OR s.next_dp >= (w.WeekStartDate)::timestamptz)
   GROUP BY w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate
)
SELECT
  ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                               w.M_Product_ID::text, w.M_Warehouse_ID::text, w.WeekStartDate::text)), 1, 10))::bit(32)::int))
           AS MD_Stock_PerWeek_V_ID,
  w.AD_Client_ID, w.AD_Org_ID, w.M_Product_ID, w.M_Warehouse_ID, w.WeekStartDate,
  COALESCE(atp_begin.qty, 0) AS QtyATPBegin,
  COALESCE(ship.qty, 0)      AS QtyExpectedShipments,
  COALESCE(recv.qty, 0)      AS QtyExpectedReceipts,
  COALESCE(atp.qty,  0)      AS QtyATP
FROM weeks w
LEFT JOIN ship      ON ship.M_Product_ID = w.M_Product_ID AND ship.M_Warehouse_ID = w.M_Warehouse_ID AND ship.WeekStartDate = w.WeekStartDate
LEFT JOIN recv      ON recv.M_Product_ID = w.M_Product_ID AND recv.M_Warehouse_ID = w.M_Warehouse_ID AND recv.WeekStartDate = w.WeekStartDate
LEFT JOIN atp       ON atp.M_Product_ID  = w.M_Product_ID AND atp.M_Warehouse_ID  = w.M_Warehouse_ID AND atp.WeekStartDate  = w.WeekStartDate
LEFT JOIN atp_begin ON atp_begin.M_Product_ID = w.M_Product_ID AND atp_begin.M_Warehouse_ID = w.M_Warehouse_ID AND atp_begin.WeekStartDate = w.WeekStartDate;
$func$;
