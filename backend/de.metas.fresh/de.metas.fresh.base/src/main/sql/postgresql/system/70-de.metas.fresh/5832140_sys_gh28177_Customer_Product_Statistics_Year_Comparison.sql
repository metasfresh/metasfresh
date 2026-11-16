SELECT db_drop_functions('report.customer_product_statistics_year_comparison')
;

CREATE FUNCTION report.Customer_Product_Statistics_Year_Comparison(
    p_AD_Org_ID numeric,
    p_C_Year_ID numeric,
    p_M_Product_ID numeric DEFAULT NULL,
    p_C_BPartner_ID numeric DEFAULT NULL
)
    RETURNS TABLE
            (
                C_BPartner_ID       numeric,
                BPValue             varchar, -- Debitor Nr.
                BPName              varchar,
                Address             varchar, -- BEST-EFFORT (bill-to location) — see hdr CTE
                Postal              varchar,
                City                varchar,
                Phone               varchar,
                Fax                 varchar,
                EMail               varchar,
                PricingSystemName   varchar, -- "Price System Name" (bp.M_PricingSystem_ID -> M_PricingSystem.Name)
                Balance             numeric, -- (B-1 FROZEN 2026-10-29) — customer open-item AR balance "Saldo" via report.open_account_balance_report (#31560 logic); see gl_open/balance CTEs
                M_Product_ID        numeric,
                ProductValue        varchar,
                ProductName         varchar,
                Qty_01              numeric,
                Qty_02              numeric,
                Qty_03              numeric,
                Qty_04              numeric,
                Qty_05              numeric,
                Qty_06              numeric,
                Qty_07              numeric,
                Qty_08              numeric,
                Qty_09              numeric,
                Qty_10              numeric,
                Qty_11              numeric,
                Qty_12              numeric,
                Qty_Total           numeric,
                Amt_01              numeric,
                Amt_02              numeric,
                Amt_03              numeric,
                Amt_04              numeric,
                Amt_05              numeric,
                Amt_06              numeric,
                Amt_07              numeric,
                Amt_08              numeric,
                Amt_09              numeric,
                Amt_10              numeric,
                Amt_11              numeric,
                Amt_12              numeric,
                Amt_Total           numeric,
                PrevQty_01          numeric,
                PrevQty_02          numeric,
                PrevQty_03          numeric,
                PrevQty_04          numeric,
                PrevQty_05          numeric,
                PrevQty_06          numeric,
                PrevQty_07          numeric,
                PrevQty_08          numeric,
                PrevQty_09          numeric,
                PrevQty_10          numeric,
                PrevQty_11          numeric,
                PrevQty_12          numeric,
                PrevQty_Total       numeric,
                PrevAmt_01          numeric,
                PrevAmt_02          numeric,
                PrevAmt_03          numeric,
                PrevAmt_04          numeric,
                PrevAmt_05          numeric,
                PrevAmt_06          numeric,
                PrevAmt_07          numeric,
                PrevAmt_08          numeric,
                PrevAmt_09          numeric,
                PrevAmt_10          numeric,
                PrevAmt_11          numeric,
                PrevAmt_12          numeric,
                PrevAmt_Total       numeric,
                Dev_01              numeric,
                Dev_02              numeric,
                Dev_03              numeric,
                Dev_04              numeric,
                Dev_05              numeric,
                Dev_06              numeric,
                Dev_07              numeric,
                Dev_08              numeric,
                Dev_09              numeric,
                Dev_10              numeric,
                Dev_11              numeric,
                Dev_12              numeric,
                Currency            varchar,
                OrgName             varchar,
                FiscalYear          varchar,
                SelectedBPName      varchar, -- echo of the p_C_BPartner_ID parameter's name (same on every row; NULL when no partner filter)
                SelectedProductName varchar  -- echo of the p_M_Product_ID parameter's name (same on every row; NULL when no product filter)
            )
    STABLE
    LANGUAGE plpgsql
AS
$$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_AD_Client_ID        numeric;
    v_Currency_ID         numeric;
    v_AcctSchema_ID       numeric; -- accounting schema (getC_AcctSchema_ID: org-only else primary) — Saldo scope (B-1)
    v_PrevYear_ID         numeric;
    v_FiscalYear          varchar;
    v_Currency            varchar;
    v_OrgName             varchar;
    v_SelectedBPName      varchar; -- echo of the p_C_BPartner_ID parameter's partner name (NULL when the param is NULL)
    v_SelectedProductName varchar; -- echo of the p_M_Product_ID parameter's product name (NULL when the param is NULL)
BEGIN
    SELECT o.AD_Client_ID INTO v_AD_Client_ID FROM AD_Org o WHERE o.AD_Org_ID = p_AD_Org_ID;
    SELECT bp.Value || ' - ' || bp.Name INTO v_SelectedBPName FROM C_BPartner bp WHERE bp.C_BPartner_ID = p_C_BPartner_ID;
    SELECT pr.Value || ' - ' || pr.Name INTO v_SelectedProductName FROM M_Product pr WHERE pr.M_Product_ID = p_M_Product_ID;
    v_AcctSchema_ID := getC_AcctSchema_ID(v_AD_Client_ID, p_AD_Org_ID);
    IF (v_AcctSchema_ID IS NULL OR v_AcctSchema_ID <= 0)
    THEN
        RAISE EXCEPTION 'No accounting schema found for AD_Org_ID=% (getC_AcctSchema_ID)', p_AD_Org_ID;
    END IF;

    SELECT acs.C_Currency_ID
    INTO v_Currency_ID
    FROM C_AcctSchema acs
    WHERE acs.C_AcctSchema_ID = v_AcctSchema_ID;

    SELECT y.FiscalYear INTO v_FiscalYear FROM C_Year y WHERE y.C_Year_ID = p_C_Year_ID;

    SELECT py.C_Year_ID
    INTO v_PrevYear_ID
    FROM C_Year y
             JOIN C_Year py ON py.C_Calendar_ID = y.C_Calendar_ID AND py.FiscalYear < y.FiscalYear
    WHERE y.C_Year_ID = p_C_Year_ID
    ORDER BY py.FiscalYear DESC
    LIMIT 1;

    SELECT c.ISO_Code INTO v_Currency FROM C_Currency c WHERE c.C_Currency_ID = v_Currency_ID;
    SELECT o.Name INTO v_OrgName FROM AD_Org o WHERE o.AD_Org_ID = p_AD_Org_ID;

    RETURN QUERY
        WITH cur AS (SELECT a.*
                     FROM report.Customer_Product_Statistics_Year_Comparison_Aggregate(p_AD_Org_ID, p_C_Year_ID) a
                     WHERE (p_M_Product_ID IS NULL OR a.M_Product_ID = p_M_Product_ID)
                       AND (p_C_BPartner_ID IS NULL OR a.C_BPartner_ID = p_C_BPartner_ID)),
             prv AS (SELECT a.*
                     FROM report.Customer_Product_Statistics_Year_Comparison_Aggregate(p_AD_Org_ID, v_PrevYear_ID) a
                     WHERE v_PrevYear_ID IS NOT NULL
                       AND (p_M_Product_ID IS NULL OR a.M_Product_ID = p_M_Product_ID)
                       AND (p_C_BPartner_ID IS NULL OR a.C_BPartner_ID = p_C_BPartner_ID)),
             m AS (SELECT 0 AS yr, cur.*
                   FROM cur
                   UNION ALL
                   SELECT 1 AS yr, prv.*
                   FROM prv),
             x AS (SELECT m.C_BPartner_ID,
                          m.M_Product_ID,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 1)  AS q01,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 2)  AS q02,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 3)  AS q03,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 4)  AS q04,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 5)  AS q05,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 6)  AS q06,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 7)  AS q07,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 8)  AS q08,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 9)  AS q09,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 10) AS q10,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 11) AS q11,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 12) AS q12,
                          SUM(m.Qty) FILTER (WHERE m.yr = 0)                     AS qt,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 1)  AS a01,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 2)  AS a02,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 3)  AS a03,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 4)  AS a04,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 5)  AS a05,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 6)  AS a06,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 7)  AS a07,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 8)  AS a08,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 9)  AS a09,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 10) AS a10,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 11) AS a11,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0 AND m.PeriodNo = 12) AS a12,
                          SUM(m.Amt) FILTER (WHERE m.yr = 0)                     AS at,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 1)  AS pq01,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 2)  AS pq02,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 3)  AS pq03,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 4)  AS pq04,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 5)  AS pq05,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 6)  AS pq06,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 7)  AS pq07,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 8)  AS pq08,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 9)  AS pq09,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 10) AS pq10,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 11) AS pq11,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 12) AS pq12,
                          SUM(m.Qty) FILTER (WHERE m.yr = 1)                     AS pqt,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 1)  AS pa01,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 2)  AS pa02,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 3)  AS pa03,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 4)  AS pa04,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 5)  AS pa05,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 6)  AS pa06,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 7)  AS pa07,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 8)  AS pa08,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 9)  AS pa09,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 10) AS pa10,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 11) AS pa11,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1 AND m.PeriodNo = 12) AS pa12,
                          SUM(m.Amt) FILTER (WHERE m.yr = 1)                     AS pat
                   FROM m
                   GROUP BY m.C_BPartner_ID, m.M_Product_ID),
             hdr AS (SELECT bp.C_BPartner_ID,
                            bp.Value     AS BPValue,
                            bp.Name      AS BPName,
                            loc.Address1 AS Address,
                            loc.Postal   AS Postal,
                            loc.City     AS City,
                            u.Phone      AS Phone,
                            u.Fax        AS Fax,
                            u.EMail      AS EMail,
                            ps.Name      AS PricingSystemName
                     FROM C_BPartner bp
                              LEFT JOIN LATERAL (SELECT cl.Address1, cl.Postal, cl.City
                                                 FROM C_BPartner_Location bpl
                                                          JOIN C_Location cl ON cl.C_Location_ID = bpl.C_Location_ID
                                                 WHERE bpl.C_BPartner_ID = bp.C_BPartner_ID
                                                   AND bpl.IsActive = 'Y'
                                                 ORDER BY (bpl.IsBillTo = 'Y') DESC, bpl.C_BPartner_Location_ID
                                                 LIMIT 1) loc ON TRUE
                              LEFT JOIN LATERAL (SELECT au.Phone, au.Fax, au.EMail
                                                 FROM AD_User au
                                                 WHERE au.C_BPartner_ID = bp.C_BPartner_ID
                                                   AND au.IsActive = 'Y'
                                                 ORDER BY au.AD_User_ID
                                                 LIMIT 1) u ON TRUE
                              LEFT JOIN M_PricingSystem ps ON ps.M_PricingSystem_ID = bp.M_PricingSystem_ID),
             gl_open AS (SELECT fa.Record_ID::numeric AS c_invoice_id,
                                SUM(d.oi_openamount)  AS open_amt_mw
                         FROM (SELECT oib.fact_acct_id, oib.oi_openamount
                               FROM report.open_account_balance_report(
                                            NOW()::date, -- p_date (as of run date)
                                            v_AcctSchema_ID, -- p_c_acctschema_id (org-only else primary)
                                            p_AD_Org_ID, -- p_ad_org_id (concrete org required)
                                            NULL::numeric, -- p_account_id = all open-item accounts
                                            NULL::numeric, -- p_c_bpartner_id = all
                                            0.05::numeric, -- p_tolerance (function default)
                                            'Y') oib -- p_showdetails = detail (fact) rows
                               WHERE oib.report_level = 'DETAIL') d
                                  JOIN Fact_Acct fa ON fa.Fact_Acct_ID = d.fact_acct_id
                         WHERE fa.AD_Table_ID = get_table_id('C_Invoice')
                         GROUP BY fa.Record_ID
                         HAVING ABS(SUM(d.oi_openamount)) > 0),
             balance AS (SELECT i.C_BPartner_ID,
                                COALESCE(SUM(g.open_amt_mw), 0) AS Balance
                         FROM gl_open g
                                  JOIN C_Invoice i ON i.C_Invoice_ID = g.c_invoice_id AND i.IsSOTrx = 'Y'
                         GROUP BY i.C_BPartner_ID)
        SELECT x.C_BPartner_ID,
               h.BPValue::varchar,
               h.BPName::varchar,
               h.Address::varchar,
               h.Postal::varchar,
               h.City::varchar,
               h.Phone::varchar,
               h.Fax::varchar,
               h.EMail::varchar,
               h.PricingSystemName::varchar,
               s.Balance,
               x.M_Product_ID,
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
               v_SelectedBPName,
               v_SelectedProductName
        FROM x
                 JOIN M_Product p ON p.M_Product_ID = x.M_Product_ID
                 LEFT JOIN hdr h ON h.C_BPartner_ID = x.C_BPartner_ID
                 LEFT JOIN balance s ON s.C_BPartner_ID = x.C_BPartner_ID
        WHERE p.AD_Org_ID IN (p_AD_Org_ID, 0)
        ORDER BY h.BPValue, p.Value;
END
$$
;
