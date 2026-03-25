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
                currency           character(3),
                margin_pct         numeric
            )
AS
$$
DECLARE
    v_is_customer boolean;
    v_is_vendor   boolean;
BEGIN
    SELECT bp.iscustomer = 'Y',
           bp.isvendor = 'Y'
    INTO v_is_customer, v_is_vendor
    FROM c_bpartner bp
    WHERE bp.c_bpartner_id = p_bpartner_id;

    IF p_bpartner_id IS NULL THEN
        v_is_customer := TRUE;
        v_is_vendor := TRUE;
    END IF;

    RETURN QUERY
        WITH params AS (SELECT (SELECT bp.value || ' - ' || bp.name
                                FROM c_bpartner bp
                                WHERE bp.c_bpartner_id = p_bpartner_id) AS bpartner_name,
                               (SELECT g.name
                                FROM c_bp_group g
                                WHERE g.c_bp_group_id = p_bp_group_id)  AS bpartner_group_name,
                               CASE
                                   WHEN p_bpartner_id IS NULL         THEN NULL
                                   WHEN v_is_customer AND v_is_vendor THEN 'both'
                                   WHEN v_is_customer                 THEN 'customer'
                                   WHEN v_is_vendor                   THEN 'vendor'
                                                                      ELSE 'unknown'
                               END                                      AS role),

             sales_orders AS (SELECT o.dateordered::date                 AS day,
                                     cur.iso_code                        AS currency,
                                     SUM(ol.qtyordered * ol.priceactual) AS revenue_net
                              FROM c_order o
                                       INNER JOIN c_orderline ol ON ol.c_order_id = o.c_order_id
                                       INNER JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
                                       INNER JOIN c_currency cur ON cur.c_currency_id = o.c_currency_id
                              WHERE o.issotrx = 'Y'
                                AND o.docstatus IN ('CO', 'CL')
                                AND v_is_customer
                                AND (p_bpartner_id IS NULL OR o.c_bpartner_id = p_bpartner_id)
                                AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                                AND (p_date_from IS NULL OR o.dateordered::date >= p_date_from::date)
                                AND (p_date_to IS NULL OR o.dateordered::date <= p_date_to::date)
                              GROUP BY o.dateordered::date, cur.iso_code),

             ar_invoices AS (SELECT i.dateinvoiced::date AS day,
                                    cur.iso_code         AS currency,
                                    SUM(i.totallines)    AS revenue_ar
                             FROM c_invoice i
                                      INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                                      INNER JOIN c_currency cur ON cur.c_currency_id = i.c_currency_id
                             WHERE i.issotrx = 'Y'
                               AND i.docstatus IN ('CO', 'CL')
                               AND v_is_customer
                               AND (p_bpartner_id IS NULL OR i.c_bpartner_id = p_bpartner_id)
                               AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                               AND (p_date_from IS NULL OR i.dateinvoiced::date >= p_date_from::date)
                               AND (p_date_to IS NULL OR i.dateinvoiced::date <= p_date_to::date)
                             GROUP BY i.dateinvoiced::date, cur.iso_code),

             ap_invoices AS (SELECT i.dateinvoiced::date AS day,
                                    cur.iso_code         AS currency,
                                    SUM(i.totallines)    AS revenue_ap
                             FROM c_invoice i
                                      INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                                      INNER JOIN c_currency cur ON cur.c_currency_id = i.c_currency_id
                             WHERE i.issotrx = 'N'
                               AND i.docstatus IN ('CO', 'CL')
                               AND v_is_vendor
                               AND (p_bpartner_id IS NULL OR i.c_bpartner_id = p_bpartner_id)
                               AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                               AND (p_date_from IS NULL OR i.dateinvoiced::date >= p_date_from::date)
                               AND (p_date_to IS NULL OR i.dateinvoiced::date <= p_date_to::date)
                             GROUP BY i.dateinvoiced::date, cur.iso_code),

             all_days_currencies AS (SELECT so.day, so.currency
                                     FROM sales_orders so
                                     UNION
                                     SELECT ar.day, ar.currency
                                     FROM ar_invoices ar
                                     UNION
                                     SELECT ap.day, ap.currency
                                     FROM ap_invoices ap)

        SELECT dc.day,
               p.bpartner_name,
               p.bpartner_group_name,
               CASE WHEN v_is_customer THEN COALESCE(so.revenue_net, 0) END AS order_revenue_net,
               CASE WHEN v_is_customer THEN COALESCE(ar.revenue_ar, 0) END  AS invoice_revenue_ar,
               CASE WHEN v_is_vendor THEN COALESCE(ap.revenue_ap, 0) END    AS invoice_revenue_ap,
               CASE
                   WHEN v_is_customer AND v_is_vendor
                       THEN COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0)
               END                                                          AS margin_cur,
               dc.currency,
               CASE
                   WHEN v_is_customer AND v_is_vendor AND COALESCE(ar.revenue_ar, 0) <> 0
                       THEN ROUND(
                           (COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0))
                               / COALESCE(ar.revenue_ar, 0) * 100,
                           1)
                   WHEN v_is_customer AND v_is_vendor
                       THEN 0
               END                                                          AS margin_pct
        FROM all_days_currencies dc
                 CROSS JOIN params p
                 LEFT JOIN sales_orders so ON so.day = dc.day AND so.currency = dc.currency
                 LEFT JOIN ar_invoices ar ON ar.day = dc.day AND ar.currency = dc.currency
                 LEFT JOIN ap_invoices ap ON ap.day = dc.day AND ap.currency = dc.currency
        ORDER BY dc.day DESC, dc.currency;

END;
$$
    LANGUAGE plpgsql
    STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(numeric, numeric, timestamp, timestamp) IS
    'Returns a daily revenue report grouped by (day, currency ISO code). No currency conversion is applied — amounts are reported in their original transaction currency.
    Partner role controls which columns are populated:
      - iscustomer=Y → order_revenue_net + invoice_revenue_ar populated, invoice_revenue_ap = NULL
      - isvendor=Y   → invoice_revenue_ap populated, order_revenue_net + invoice_revenue_ar = NULL
      - both flags   → all columns populated, margin calculated per currency
      - no filter    → full report (all columns, all currencies)
    Parameters:
      p_bpartner_id – filter by business partner; role auto-detected from iscustomer/isvendor flags
      p_bp_group_id – filter by partner group
      p_date_from   – start of date range, inclusive
      p_date_to     – end of date range, inclusive'
;
