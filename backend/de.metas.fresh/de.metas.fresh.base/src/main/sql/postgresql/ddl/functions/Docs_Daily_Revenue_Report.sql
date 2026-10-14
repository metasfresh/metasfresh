DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(
    numeric,
    numeric,
    timestamp,
    timestamp
)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(
    p_bpartner_id numeric DEFAULT NULL,
    p_bp_group_id numeric DEFAULT NULL,
    p_date_from   timestamp DEFAULT NULL,
    p_date_to     timestamp DEFAULT NULL
)
    RETURNS TABLE
            (
                day                date,
                bpartner           text,
                bpartner_group     character varying(60),
                order_revenue_net  numeric,
                invoice_revenue_ar numeric,
                invoice_revenue_ap numeric,
                margin_cur         numeric,
                cursymbol          character varying,
                margin_pct         numeric
            )
AS
$$
DECLARE
    v_cursymbol character varying;
BEGIN

    -- Resolve accounting currency symbol, scoped to a single active client
    SELECT cur.cursymbol
    INTO v_cursymbol
    FROM AD_ClientInfo ci
             INNER JOIN C_AcctSchema ac ON ci.c_acctschema1_id = ac.c_acctschema_id
             INNER JOIN c_currency cur ON cur.c_currency_id = ac.C_Currency_ID
    WHERE ac.isaccrual = 'Y'
      AND ci.AD_Client_ID > 0
    ORDER BY ci.AD_Client_ID
    LIMIT 1;

    RETURN QUERY
        WITH params AS (SELECT (SELECT bp.value || ' - ' || bp.name
                                FROM c_bpartner bp
                                WHERE bp.c_bpartner_id = p_bpartner_id) AS bpartner_name,
                               (SELECT g.name
                                FROM c_bp_group g
                                WHERE g.c_bp_group_id = p_bp_group_id)  AS bpartner_group_name),

             sales_orders AS (SELECT o.dateordered::date            AS day,
                                     SUM(currencyBase(ol.linenetamt,
                                                      o.c_currency_id,
                                                      o.dateordered,
                                                      o.AD_Client_ID,
                                                      o.AD_Org_ID)) AS revenue_net
                              FROM c_order o
                                       INNER JOIN c_orderline ol ON ol.c_order_id = o.c_order_id
                                       INNER JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
                                       INNER JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctypetarget_id
                              WHERE o.issotrx = 'Y'
                                AND o.docstatus IN ('CO', 'CL')
                                AND dt.docsubtype NOT IN ('OA', 'ON') -- exclude offers and requisitions
                                AND (p_bpartner_id IS NULL OR o.c_bpartner_id = p_bpartner_id)
                                AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                                AND (p_date_from IS NULL OR o.dateordered::date >= p_date_from::date)
                                AND (p_date_to IS NULL OR o.dateordered::date <= p_date_to::date)
                              GROUP BY o.dateordered::date),

             ar_invoices AS (SELECT i.dateinvoiced::date           AS day,
                                    SUM(currencyBase(i.totallines,
                                                     i.c_currency_id,
                                                     i.dateinvoiced,
                                                     i.AD_Client_ID,
                                                     i.AD_Org_ID)) AS revenue_ar
                             FROM c_invoice i
                                      INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                             WHERE i.issotrx = 'Y'
                               AND i.docstatus IN ('CO', 'CL')
                               AND (p_bpartner_id IS NULL OR i.c_bpartner_id = p_bpartner_id)
                               AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                               AND (p_date_from IS NULL OR i.dateinvoiced::date >= p_date_from::date)
                               AND (p_date_to IS NULL OR i.dateinvoiced::date <= p_date_to::date)
                             GROUP BY i.dateinvoiced::date),

             ap_invoices AS (SELECT i.dateinvoiced::date           AS day,
                                    SUM(currencyBase(i.totallines,
                                                     i.c_currency_id,
                                                     i.dateinvoiced,
                                                     i.AD_Client_ID,
                                                     i.AD_Org_ID)) AS revenue_ap
                             FROM c_invoice i
                                      INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                             WHERE i.issotrx = 'N'
                               AND i.docstatus IN ('CO', 'CL')
                               AND (p_bpartner_id IS NULL OR i.c_bpartner_id = p_bpartner_id)
                               AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                               AND (p_date_from IS NULL OR i.dateinvoiced::date >= p_date_from::date)
                               AND (p_date_to IS NULL OR i.dateinvoiced::date <= p_date_to::date)
                             GROUP BY i.dateinvoiced::date),

             all_days AS (SELECT so.day
                          FROM sales_orders so
                          UNION
                          SELECT ar.day
                          FROM ar_invoices ar
                          UNION
                          SELECT ap.day
                          FROM ap_invoices ap)

        SELECT dc.day,
               p.bpartner_name,
               p.bpartner_group_name,
               so.revenue_net                                          AS order_revenue_net,
               ar.revenue_ar                                           AS invoice_revenue_ar,
               ap.revenue_ap                                           AS invoice_revenue_ap,
               COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0) AS margin_cur,
               v_cursymbol                                             AS cursymbol,
               CASE
                   WHEN ar.revenue_ar <> 0 THEN ROUND(
                           (COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0))
                               / COALESCE(ar.revenue_ar, 1) * 100,
                           1)
                                           ELSE -ROUND(
                                                   COALESCE(ap.revenue_ap, 0)
                                                       / COALESCE(ap.revenue_ap, 1) * 100,
                                                   1)
               END
                                                                       AS margin_pct
        FROM all_days dc
                 CROSS JOIN params p
                 LEFT JOIN sales_orders so ON so.day = dc.day
                 LEFT JOIN ar_invoices ar ON ar.day = dc.day
                 LEFT JOIN ap_invoices ap ON ap.day = dc.day
        ORDER BY dc.day DESC;

END;
$$
    LANGUAGE plpgsql
    STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(numeric, numeric, timestamp, timestamp) IS
    'Returns a daily revenue report aggregated by day. Amounts converted to accounting currency via currencyBase().
    Margin logic is derived from actual data presence (NULL = no transactions that day for that side):
      - AR + AP present → margin = AR - AP; margin_pct = (AR-AP)/AR*100
      - AR only         → margin = AR (no cost side); margin_pct = 100
      - AP only         → margin = -AP; margin_pct = -100 (no revenue side)
    Offers excluded via docsubtype NOT IN (OA, ON) on c_doctypetarget_id.
    Parameters:
      p_bpartner_id – filter by business partner
      p_bp_group_id – filter by partner group
      p_date_from   – start of date range, inclusive
      p_date_to     – end of date range, inclusive'
;
