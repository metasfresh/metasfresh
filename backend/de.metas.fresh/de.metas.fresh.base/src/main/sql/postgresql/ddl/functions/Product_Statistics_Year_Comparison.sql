SELECT db_drop_functions('report.product_statistics_year_comparison')
;

SELECT db_drop_functions('report.product_statistics_year_comparison_aggregate')
;

-- Aggregation helper of the product statistics year comparison: product x period (standard periods of the given year) x quantity x amount.
-- Amount: the posted amount (Fact_Acct, AmtAcctCr - AmtAcctDr, all accounts of the line) of completed/closed sales invoice and credit-note lines,
--   in the accounting schema the organisation posts to (getC_AcctSchema_ID), by period of Fact_Acct.DateAcct; as report.fresh_statistics, no conversion.
--   A completed/closed invoice that is not posted (yet, or its posting failed) adds nothing and is counted in NotPosted_Count (by period of C_Invoice.DateAcct).
--   Only invoices with C_Invoice.DateAcct in the year are read (an invoice is posted on its DateAcct).
-- Quantity: completed/closed customer shipments (C-) minus customer returns (C+, but not service repair / empties correction), by period of M_InOut.MovementDate.
-- Both sources are aggregated separately and joined with a full outer join. Packing material is excluded (the amount side by product category only).
-- NULL when a product has no amount (or no quantity) in a period.
CREATE FUNCTION report.Product_Statistics_Year_Comparison_Aggregate(
    p_AD_Org_ID numeric,
    p_C_Year_ID numeric
)
    RETURNS TABLE
            (
                M_Product_ID       numeric,
                PeriodNo           integer,
                PeriodStart        date,
                Qty                numeric,
                Amt                numeric,
                NotPosted_Count    integer
            )
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_AD_Client_ID    numeric;
    v_C_AcctSchema_ID numeric;
    v_InvoiceTable_ID numeric;
    v_PackingCat      numeric;
BEGIN
    SELECT o.AD_Client_ID INTO v_AD_Client_ID FROM AD_Org o WHERE o.AD_Org_ID = p_AD_Org_ID;

    v_C_AcctSchema_ID := getC_AcctSchema_ID(v_AD_Client_ID, p_AD_Org_ID);
    IF (v_C_AcctSchema_ID IS NULL OR v_C_AcctSchema_ID <= 0)
    THEN
        RAISE EXCEPTION 'No accounting schema found for AD_Org_ID=% (getC_AcctSchema_ID); cannot read the posted amounts', p_AD_Org_ID;
    END IF;

    v_InvoiceTable_ID := get_Table_ID('C_Invoice');
    v_PackingCat := getSysConfigAsNumeric('PackingMaterialProductCategoryID', v_AD_Client_ID, p_AD_Org_ID);

    RETURN QUERY
        WITH periods AS (SELECT p.C_Period_ID, p.PeriodNo::integer AS PeriodNo, p.StartDate::date AS StartDate, p.EndDate::date AS EndDate
                         FROM C_Period p
                         WHERE p.C_Year_ID = p_C_Year_ID
                           AND p.PeriodType = 'S'),
             inv_lines AS (SELECT il.M_Product_ID,
                                  il.C_InvoiceLine_ID,
                                  i.C_Invoice_ID,
                                  i.Posted,
                                  i.DateAcct
                           FROM C_InvoiceLine il
                                    JOIN C_Invoice i ON i.C_Invoice_ID = il.C_Invoice_ID
                                    JOIN M_Product pr ON pr.M_Product_ID = il.M_Product_ID
                           WHERE i.IsSOTrx = 'Y'
                             AND i.DocStatus IN ('CO', 'CL')
                             AND i.AD_Org_ID = p_AD_Org_ID
                             AND i.DateAcct::date BETWEEN (SELECT MIN(per.StartDate) FROM periods per) AND (SELECT MAX(per.EndDate) FROM periods per)
                             AND il.M_Product_ID IS NOT NULL
                             AND pr.M_Product_Category_ID IS DISTINCT FROM v_PackingCat),
             amt AS (SELECT l.M_Product_ID,
                            per.PeriodNo,
                            per.StartDate,
                            SUM(fa.AmtAcctCr - fa.AmtAcctDr) AS Amt
                     FROM inv_lines l
                              JOIN Fact_Acct fa ON fa.AD_Table_ID = v_InvoiceTable_ID
                         AND fa.Record_ID = l.C_Invoice_ID
                         AND fa.Line_ID = l.C_InvoiceLine_ID
                         AND fa.C_AcctSchema_ID = v_C_AcctSchema_ID
                         AND fa.PostingType = 'A'
                         AND fa.IsActive = 'Y'
                              JOIN periods per ON fa.DateAcct::date BETWEEN per.StartDate AND per.EndDate
                     GROUP BY l.M_Product_ID, per.PeriodNo, per.StartDate),
             not_posted AS (SELECT l.M_Product_ID,
                                   per.PeriodNo,
                                   per.StartDate,
                                   COUNT(DISTINCT l.C_Invoice_ID)::integer AS NotPosted_Count
                            FROM inv_lines l
                                     JOIN periods per ON l.DateAcct::date BETWEEN per.StartDate AND per.EndDate
                            WHERE l.Posted <> 'Y'
                            GROUP BY l.M_Product_ID, per.PeriodNo, per.StartDate),
             qty AS (SELECT iol.M_Product_ID,
                            per.PeriodNo,
                            per.StartDate,
                            SUM(CASE WHEN io.MovementType = 'C-' THEN iol.MovementQty ELSE -iol.MovementQty END) AS Qty
                     FROM M_InOutLine iol
                              JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
                              LEFT JOIN C_DocType dt ON dt.C_DocType_ID = io.C_DocType_ID
                              JOIN M_Product pr ON pr.M_Product_ID = iol.M_Product_ID
                              JOIN periods per ON io.MovementDate::date BETWEEN per.StartDate AND per.EndDate
                     WHERE io.MovementType IN ('C-', 'C+')
                       AND (io.MovementType = 'C-' OR COALESCE(dt.DocSubType, '') NOT IN ('SR', 'EC'))
                       AND io.DocStatus IN ('CO', 'CL')
                       AND io.AD_Org_ID = p_AD_Org_ID
                       AND iol.IsPackagingMaterial = 'N'
                       AND iol.M_Product_ID IS NOT NULL
                       AND pr.M_Product_Category_ID IS DISTINCT FROM v_PackingCat
                     GROUP BY iol.M_Product_ID, per.PeriodNo, per.StartDate)
        SELECT COALESCE(q.M_Product_ID, a.M_Product_ID, np.M_Product_ID),
               COALESCE(q.PeriodNo, a.PeriodNo, np.PeriodNo),
               COALESCE(q.StartDate, a.StartDate, np.StartDate),
               q.Qty,
               a.Amt,
               COALESCE(np.NotPosted_Count, 0)
        FROM qty q
                 FULL OUTER JOIN amt a ON a.M_Product_ID = q.M_Product_ID AND a.PeriodNo = q.PeriodNo
                 FULL OUTER JOIN not_posted np ON np.M_Product_ID = COALESCE(q.M_Product_ID, a.M_Product_ID) AND np.PeriodNo = COALESCE(q.PeriodNo, a.PeriodNo);
END
$$
;

-- Product statistics year comparison: one row per product with 12 monthly quantities/amounts of the chosen year, the same of the previous year
-- (the C_Year of the same calendar with the next lower FiscalYear; NULL values when there is none) and the 12 monthly quantity deviations in percent.
-- Totals are NULL when no month has a value. Dev_xx = ROUND(NULLIF(raw, 0), 2): blank when exactly 0; no total deviation.
-- NotPosted_Count / NotPosted_Months: completed sales invoices / credit notes that are not posted, in the chosen and the previous year (distinct YYYY-MM, ascending).
-- Only products of p_AD_Org_ID or of organisation * (0), as offered by the product parameter (AD_Val_Rule 540377).
CREATE FUNCTION report.Product_Statistics_Year_Comparison(
    p_AD_Org_ID    numeric,
    p_C_Year_ID    numeric,
    p_M_Product_ID numeric DEFAULT NULL
)
    RETURNS TABLE
            (
                M_Product_ID       numeric,
                ProductValue       varchar,
                ProductName        varchar,
                Qty_01             numeric, Qty_02 numeric, Qty_03 numeric, Qty_04 numeric, Qty_05 numeric, Qty_06 numeric,
                Qty_07             numeric, Qty_08 numeric, Qty_09 numeric, Qty_10 numeric, Qty_11 numeric, Qty_12 numeric,
                Qty_Total          numeric,
                Amt_01             numeric, Amt_02 numeric, Amt_03 numeric, Amt_04 numeric, Amt_05 numeric, Amt_06 numeric,
                Amt_07             numeric, Amt_08 numeric, Amt_09 numeric, Amt_10 numeric, Amt_11 numeric, Amt_12 numeric,
                Amt_Total          numeric,
                PrevQty_01         numeric, PrevQty_02 numeric, PrevQty_03 numeric, PrevQty_04 numeric, PrevQty_05 numeric, PrevQty_06 numeric,
                PrevQty_07         numeric, PrevQty_08 numeric, PrevQty_09 numeric, PrevQty_10 numeric, PrevQty_11 numeric, PrevQty_12 numeric,
                PrevQty_Total      numeric,
                PrevAmt_01         numeric, PrevAmt_02 numeric, PrevAmt_03 numeric, PrevAmt_04 numeric, PrevAmt_05 numeric, PrevAmt_06 numeric,
                PrevAmt_07         numeric, PrevAmt_08 numeric, PrevAmt_09 numeric, PrevAmt_10 numeric, PrevAmt_11 numeric, PrevAmt_12 numeric,
                PrevAmt_Total      numeric,
                Dev_01             numeric, Dev_02 numeric, Dev_03 numeric, Dev_04 numeric, Dev_05 numeric, Dev_06 numeric,
                Dev_07             numeric, Dev_08 numeric, Dev_09 numeric, Dev_10 numeric, Dev_11 numeric, Dev_12 numeric,
                Currency           varchar,
                OrgName            varchar,
                FiscalYear         varchar,
                NotPosted_Count    integer,
                NotPosted_Months   varchar
            )
    STABLE
    LANGUAGE plpgsql
AS
$$
#variable_conflict use_column
DECLARE
    v_PrevYear_ID  numeric;
    v_FiscalYear   varchar;
    v_Currency     varchar;
    v_OrgName      varchar;
BEGIN
    SELECT y.FiscalYear INTO v_FiscalYear FROM C_Year y WHERE y.C_Year_ID = p_C_Year_ID;

    SELECT py.C_Year_ID
    INTO v_PrevYear_ID
    FROM C_Year y
             JOIN C_Year py ON py.C_Calendar_ID = y.C_Calendar_ID AND py.FiscalYear < y.FiscalYear
    WHERE y.C_Year_ID = p_C_Year_ID
    ORDER BY py.FiscalYear DESC
    LIMIT 1;

    SELECT c.ISO_Code
    INTO v_Currency
    FROM AD_Org o
             JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = getC_AcctSchema_ID(o.AD_Client_ID, o.AD_Org_ID)
             JOIN C_Currency c ON c.C_Currency_ID = acs.C_Currency_ID
    WHERE o.AD_Org_ID = p_AD_Org_ID;

    SELECT o.Name INTO v_OrgName FROM AD_Org o WHERE o.AD_Org_ID = p_AD_Org_ID;

    RETURN QUERY
        WITH cur AS (SELECT a.* FROM report.Product_Statistics_Year_Comparison_Aggregate(p_AD_Org_ID, p_C_Year_ID) a
                     WHERE p_M_Product_ID IS NULL OR a.M_Product_ID = p_M_Product_ID),
             prv AS (SELECT a.* FROM report.Product_Statistics_Year_Comparison_Aggregate(p_AD_Org_ID, v_PrevYear_ID) a
                     WHERE v_PrevYear_ID IS NOT NULL AND (p_M_Product_ID IS NULL OR a.M_Product_ID = p_M_Product_ID)),
             m AS (SELECT 0 AS yr, cur.* FROM cur
                   UNION ALL
                   SELECT 1 AS yr, prv.* FROM prv),
             x AS (SELECT m.M_Product_ID,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 1) AS q01,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 2) AS q02,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 3) AS q03,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 4) AS q04,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 5) AS q05,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 6) AS q06,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 7) AS q07,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 8) AS q08,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 9) AS q09,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 10) AS q10,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 11) AS q11,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 12) AS q12,
                            SUM(m.Qty) FILTER (WHERE m.yr = 0) AS qt,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 1) AS a01,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 2) AS a02,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 3) AS a03,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 4) AS a04,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 5) AS a05,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 6) AS a06,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 7) AS a07,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 8) AS a08,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 9) AS a09,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 10) AS a10,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 11) AS a11,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 12) AS a12,
                            SUM(m.Amt) FILTER (WHERE m.yr = 0) AS at,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 1) AS pq01,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 2) AS pq02,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 3) AS pq03,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 4) AS pq04,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 5) AS pq05,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 6) AS pq06,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 7) AS pq07,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 8) AS pq08,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 9) AS pq09,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 10) AS pq10,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 11) AS pq11,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 12) AS pq12,
                            SUM(m.Qty) FILTER (WHERE m.yr = 1) AS pqt,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 1) AS pa01,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 2) AS pa02,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 3) AS pa03,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 4) AS pa04,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 5) AS pa05,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 6) AS pa06,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 7) AS pa07,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 8) AS pa08,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 9) AS pa09,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 10) AS pa10,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 11) AS pa11,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 12) AS pa12,
                            SUM(m.Amt) FILTER (WHERE m.yr = 1) AS pat,
                            SUM(m.NotPosted_Count)::integer AS np_cnt,
                            string_agg(DISTINCT to_char(m.PeriodStart, 'YYYY-MM'), ', ' ORDER BY to_char(m.PeriodStart, 'YYYY-MM'))
                            FILTER (WHERE m.NotPosted_Count > 0) AS np_months
                     FROM m
                     GROUP BY m.M_Product_ID)
        SELECT x.M_Product_ID,
               p.Value::varchar,
               p.Name::varchar,
               x.q01,
               x.q02,
               x.q03,
               x.q04,
               x.q05,
               x.q06,
               x.q07,
               x.q08,
               x.q09,
               x.q10,
               x.q11,
               x.q12,
               x.qt,
               x.a01,
               x.a02,
               x.a03,
               x.a04,
               x.a05,
               x.a06,
               x.a07,
               x.a08,
               x.a09,
               x.a10,
               x.a11,
               x.a12,
               x.at,
               x.pq01,
               x.pq02,
               x.pq03,
               x.pq04,
               x.pq05,
               x.pq06,
               x.pq07,
               x.pq08,
               x.pq09,
               x.pq10,
               x.pq11,
               x.pq12,
               x.pqt,
               x.pa01,
               x.pa02,
               x.pa03,
               x.pa04,
               x.pa05,
               x.pa06,
               x.pa07,
               x.pa08,
               x.pa09,
               x.pa10,
               x.pa11,
               x.pa12,
               x.pat,
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq01, 0) <> 0 THEN (COALESCE(x.q01, 0) / COALESCE(x.pq01, 0) - 1) * 100 WHEN COALESCE(x.q01, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq02, 0) <> 0 THEN (COALESCE(x.q02, 0) / COALESCE(x.pq02, 0) - 1) * 100 WHEN COALESCE(x.q02, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq03, 0) <> 0 THEN (COALESCE(x.q03, 0) / COALESCE(x.pq03, 0) - 1) * 100 WHEN COALESCE(x.q03, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq04, 0) <> 0 THEN (COALESCE(x.q04, 0) / COALESCE(x.pq04, 0) - 1) * 100 WHEN COALESCE(x.q04, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq05, 0) <> 0 THEN (COALESCE(x.q05, 0) / COALESCE(x.pq05, 0) - 1) * 100 WHEN COALESCE(x.q05, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq06, 0) <> 0 THEN (COALESCE(x.q06, 0) / COALESCE(x.pq06, 0) - 1) * 100 WHEN COALESCE(x.q06, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq07, 0) <> 0 THEN (COALESCE(x.q07, 0) / COALESCE(x.pq07, 0) - 1) * 100 WHEN COALESCE(x.q07, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq08, 0) <> 0 THEN (COALESCE(x.q08, 0) / COALESCE(x.pq08, 0) - 1) * 100 WHEN COALESCE(x.q08, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq09, 0) <> 0 THEN (COALESCE(x.q09, 0) / COALESCE(x.pq09, 0) - 1) * 100 WHEN COALESCE(x.q09, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq10, 0) <> 0 THEN (COALESCE(x.q10, 0) / COALESCE(x.pq10, 0) - 1) * 100 WHEN COALESCE(x.q10, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq11, 0) <> 0 THEN (COALESCE(x.q11, 0) / COALESCE(x.pq11, 0) - 1) * 100 WHEN COALESCE(x.q11, 0) <> 0 THEN 100 END, 0), 2),
               ROUND(NULLIF(CASE WHEN COALESCE(x.pq12, 0) <> 0 THEN (COALESCE(x.q12, 0) / COALESCE(x.pq12, 0) - 1) * 100 WHEN COALESCE(x.q12, 0) <> 0 THEN 100 END, 0), 2),
               v_Currency,
               v_OrgName,
               v_FiscalYear,
               x.np_cnt,
               x.np_months::varchar
        FROM x
                 JOIN M_Product p ON p.M_Product_ID = x.M_Product_ID
        WHERE p.AD_Org_ID IN (p_AD_Org_ID, 0)
        ORDER BY p.Value;
END
$$
;
