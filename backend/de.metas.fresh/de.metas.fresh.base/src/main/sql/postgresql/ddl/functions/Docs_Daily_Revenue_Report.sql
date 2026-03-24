DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(p_ad_client_id      numeric,
                                                                                     p_ad_org_id         numeric,
                                                                                     p_customer_id       numeric,
                                                                                     p_customer_group_id numeric,
                                                                                     p_vendor_id         numeric,
                                                                                     p_vendor_group_id   numeric,
                                                                                     p_date_from         timestamp,
                                                                                     p_date_to           timestamp)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(p_ad_client_id      numeric DEFAULT NULL,
                                                                                        p_ad_org_id         numeric DEFAULT NULL,
                                                                                        p_customer_id       numeric DEFAULT NULL,
                                                                                        p_customer_group_id numeric DEFAULT NULL,
                                                                                        p_vendor_id         numeric DEFAULT NULL,
                                                                                        p_vendor_group_id   numeric DEFAULT NULL,
                                                                                        p_date_from         timestamp DEFAULT NULL,
                                                                                        p_date_to           timestamp DEFAULT NULL)
    RETURNS TABLE
            (
                day                date,
                client             text,
                org                text,
                customer           text,
                customer_group     text,
                vendor             text,
                vendor_group       text,
                order_revenue_net  numeric, -- Tagesumsatz
                invoice_revenue_ar numeric, -- Summe VK = Rechnungsumsatz Debitoren = Accounts Receivable
                invoice_revenue_ap numeric, -- Summe EK = Rechnungsumsatz Kreditoren = Accounts Payable
                margin_eur         numeric, -- Marge in €
                margin_pct         numeric  -- Marge in %
            )
AS
$$
WITH params AS (SELECT (SELECT c.name FROM ad_client c WHERE c.ad_client_id = p_ad_client_id)                          AS client_name,
                       (SELECT o.name FROM ad_org o WHERE o.ad_org_id = p_ad_org_id)                                   AS org_name,
                       (SELECT bp.value || ' - ' || bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = p_customer_id) AS customer_name,
                       (SELECT g.name FROM c_bp_group g WHERE g.c_bp_group_id = p_customer_group_id)                   AS customer_group_name,
                       (SELECT bp.value || ' - ' || bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = p_vendor_id)   AS vendor_name,
                       (SELECT g.name FROM c_bp_group g WHERE g.c_bp_group_id = p_vendor_group_id)                     AS vendor_group_name),

     sales_orders AS (SELECT o.dateordered::date                 AS day,
                             SUM(ol.qtyordered * ol.priceactual) AS revenue_net
                      FROM c_order o
                               INNER JOIN c_orderline ol ON ol.c_order_id = o.c_order_id
                               INNER JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
                      WHERE o.issotrx = 'Y'
                        AND o.docstatus IN ('CO', 'CL')
                        AND (p_ad_client_id IS NULL OR o.ad_client_id = p_ad_client_id)
                        AND (p_ad_org_id IS NULL OR o.ad_org_id = p_ad_org_id)
                        AND (p_customer_id IS NULL OR o.c_bpartner_id = p_customer_id)
                        AND (p_customer_group_id IS NULL OR bp.c_bp_group_id = p_customer_group_id)
                        AND (p_date_from IS NULL OR o.dateordered::date >= p_date_from::date)
                        AND (p_date_to IS NULL OR o.dateordered::date <= p_date_to::date)
                      GROUP BY o.dateordered::date),

     ar_invoices AS (SELECT i.dateinvoiced::date AS day,
                            SUM(i.totallines)    AS revenue_ar
                     FROM c_invoice i
                              INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                     WHERE i.issotrx = 'Y'
                       AND i.docstatus IN ('CO', 'CL')
                       AND (p_ad_client_id IS NULL OR i.ad_client_id = p_ad_client_id)
                       AND (p_ad_org_id IS NULL OR i.ad_org_id = p_ad_org_id)
                       AND (p_customer_id IS NULL OR i.c_bpartner_id = p_customer_id)
                       AND (p_customer_group_id IS NULL OR bp.c_bp_group_id = p_customer_group_id)
                       AND (p_date_from IS NULL OR i.dateinvoiced::date >= p_date_from::date)
                       AND (p_date_to IS NULL OR i.dateinvoiced::date <= p_date_to::date)
                     GROUP BY i.dateinvoiced::date),

     ap_invoices AS (SELECT i.dateinvoiced::date AS day,
                            SUM(i.totallines)    AS revenue_ap
                     FROM c_invoice i
                              INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                     WHERE i.issotrx = 'N'
                       AND i.docstatus IN ('CO', 'CL')
                       AND (p_ad_client_id IS NULL OR i.ad_client_id = p_ad_client_id)
                       AND (p_ad_org_id IS NULL OR i.ad_org_id = p_ad_org_id)
                       AND (p_vendor_id IS NULL OR i.c_bpartner_id = p_vendor_id)
                       AND (p_vendor_group_id IS NULL OR bp.c_bp_group_id = p_vendor_group_id)
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

SELECT d.day,
       p.client_name,
       p.org_name,
       p.customer_name,
       p.customer_group_name,
       p.vendor_name,
       p.vendor_group_name,
       COALESCE(so.revenue_net, 0)                             AS order_revenue_net,
       COALESCE(ar.revenue_ar, 0)                              AS invoice_revenue_ar,
       COALESCE(ap.revenue_ap, 0)                              AS invoice_revenue_ap,
       COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0) AS margin_eur,
       CASE
           WHEN COALESCE(ar.revenue_ar, 0) = 0 THEN 0
                                               ELSE ROUND(
                                                       (COALESCE(ar.revenue_ar, 0) - COALESCE(ap.revenue_ap, 0))
                                                           / COALESCE(ar.revenue_ar, 0) * 100, 1
                                                    )
       END                                                     AS margin_pct
FROM all_days d
         CROSS JOIN params p
         LEFT JOIN sales_orders so ON so.day = d.day
         LEFT JOIN ar_invoices ar ON ar.day = d.day
         LEFT JOIN ap_invoices ap ON ap.day = d.day
ORDER BY d.day DESC;
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Revenue_Report(numeric, numeric, numeric, numeric, numeric, numeric, timestamp, timestamp) IS
    'Returns a daily revenue report aggregated by day, including order revenue (sales orders), AR invoice revenue (customers), AP invoice revenue (vendors), and the resulting margin in EUR and %.
    Result columns include the resolved names of the filter parameters (client, org, customer, customer group, vendor, vendor group) — NULL when no filter was applied.
Parameters:
  p_ad_client_id      – restrict to a specific client (optional)
  p_ad_org_id         – restrict to a specific organisation (optional)
  p_customer_id       – filter sales orders and AR invoices by customer (c_bpartner_id) (optional)
  p_customer_group_id – filter sales orders and AR invoices by customer group (c_bp_group_id) (optional)
  p_vendor_id         – filter AP invoices by vendor (c_bpartner_id) (optional)
  p_vendor_group_id   – filter AP invoices by vendor group (c_bp_group_id) (optional)
  p_date_from         – start of date range, inclusive (optional)
  p_date_to           – end of date range, inclusive (optional)'
;
