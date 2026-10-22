-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Daily_Delivery_Revenue_Report.sql
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Daily_Delivery_Revenue_Report(
    numeric,
    numeric,
    timestamp,
    timestamp
)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Delivery_Revenue_Report(
    p_bpartner_id numeric DEFAULT NULL,
    p_bp_group_id numeric DEFAULT NULL,
    p_date_from   timestamp DEFAULT NULL,
    p_date_to     timestamp DEFAULT NULL
)
    RETURNS TABLE
            (
                day                    date,
                bpartner               text,
                bpartner_group         character varying(60),
                delivery_revenue_net   numeric,
                cursymbol              character varying
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

             -- Every completed sales-delivery line in scope, one row per M_InOutLine.
             delivery_lines AS (SELECT io.m_inout_id,
                                       iol.m_inoutline_id,
                                       io.movementdate::date AS day,
                                       io.AD_Client_ID,
                                       io.AD_Org_ID
                                FROM m_inout io
                                         INNER JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id
                                         INNER JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
                                WHERE io.issotrx = 'Y'
                                  AND io.docstatus IN ('CO', 'CL')
                                  AND (p_bpartner_id IS NULL OR io.c_bpartner_id = p_bpartner_id)
                                  AND (p_bp_group_id IS NULL OR bp.c_bp_group_id = p_bp_group_id)
                                  AND (p_date_from IS NULL OR io.movementdate::date >= p_date_from::date)
                                  AND (p_date_to IS NULL OR io.movementdate::date <= p_date_to::date)),

             -- Invoiced portion per delivery line = the actual net of the invoice line(s) matched back to it,
             -- and the invoiced quantity those lines cover. C_InvoiceLine.QtyInvoiced/LineNetAmt are written
             -- synchronously when the invoice is created -- unlike C_Invoice_Candidate.QtyInvoiced, which is
             -- only brought current by a later, separate recompute pass (see open_portion note below) -- so
             -- this is the authoritative, already-settled source for "how much of this line is invoiced".
             invoiced_portion AS (SELECT dl.m_inoutline_id,
                                         SUM(ciline.linenetamt)  AS invoiced_net,
                                         SUM(ciline.qtyinvoiced) AS invoiced_qty
                                  FROM delivery_lines dl
                                           INNER JOIN c_invoiceline ciline ON ciline.m_inoutline_id = dl.m_inoutline_id
                                                      AND ciline.isactive = 'Y'
                                           INNER JOIN c_invoice i ON i.c_invoice_id = ciline.c_invoice_id
                                                      AND i.docstatus IN ('CO', 'CL')
                                  GROUP BY dl.m_inoutline_id),

             -- Open (not-yet-invoiced) portion per delivery line = IC per-unit price x open qty.
             -- Open qty = C_Invoice_Candidate.QtyDelivered - (qty already invoiced per invoiced_portion
             -- above) -- NOT C_Invoice_Candidate.QtyInvoiced: that column is only brought current by a
             -- later, separate recompute pass, so reading it right after invoicing can race and still see
             -- a stale 0 even though the invoice (and its C_InvoiceLine.QtyInvoiced) already committed.
             -- Also NOT C_InvoiceCandidate_InOutLine.QtyInvoiced, which stays 0 even after the IC is fully
             -- invoiced (that column tracks a different concern).
             -- Rounded to currency precision at this grain (quantity coverage, not cent-reconstruction).
             --
             -- Open-qty delivered term uses the IC-level C_Invoice_Candidate.QtyDelivered (not the per-link
             -- C_InvoiceCandidate_InOutLine.QtyDelivered) for two reasons: (i) ordinary sales delivery lines
             -- each get a dedicated 1:1 invoice candidate, so the two are numerically equal, and (ii) the
             -- IC-level column carries the correct negative sign for customer-return lines, which the
             -- per-link column does not. Packaging-material lines (one IC aggregated across several
             -- M_InOutLines) are out of scope for this report (REQUIREMENTS section 3-5) and would need
             -- revisiting here if ever required.
             open_portion AS (SELECT dl.m_inoutline_id,
                                     SUM(ROUND(COALESCE(ic.priceactual, 0)
                                                   * (ic.qtydelivered - COALESCE(ip.invoiced_qty, 0)), 2)) AS open_net
                              FROM delivery_lines dl
                                       INNER JOIN c_invoicecandidate_inoutline cic_iol
                                                  ON cic_iol.m_inoutline_id = dl.m_inoutline_id
                                                      AND cic_iol.isactive = 'Y'
                                       INNER JOIN c_invoice_candidate ic
                                                  ON ic.c_invoice_candidate_id = cic_iol.c_invoice_candidate_id
                                                      AND ic.isactive = 'Y'
                                       LEFT JOIN invoiced_portion ip ON ip.m_inoutline_id = dl.m_inoutline_id
                              WHERE (ic.qtydelivered - COALESCE(ip.invoiced_qty, 0)) <> 0
                              GROUP BY dl.m_inoutline_id),

             -- Per-line net value = invoiced portion + open portion (each rounded independently at its own
             -- grain -- quantity coverage, not cent-reconstruction; see CONCEPT.md).
             line_values AS (SELECT dl.m_inoutline_id,
                                    dl.day,
                                    dl.AD_Client_ID,
                                    dl.AD_Org_ID,
                                    COALESCE(ip.invoiced_net, 0) + COALESCE(op.open_net, 0) AS line_net
                             FROM delivery_lines dl
                                      LEFT JOIN invoiced_portion ip ON ip.m_inoutline_id = dl.m_inoutline_id
                                      LEFT JOIN open_portion op ON op.m_inoutline_id = dl.m_inoutline_id),

             daily_revenue AS (SELECT lv.day                                     AS day,
                                      SUM(currencyBase(lv.line_net,
                                                        (SELECT ac.c_currency_id
                                                         FROM C_AcctSchema ac
                                                         WHERE ac.isaccrual = 'Y'
                                                           AND ac.AD_Client_ID = lv.AD_Client_ID
                                                         LIMIT 1),
                                                        lv.day::timestamp,
                                                        lv.AD_Client_ID,
                                                        lv.AD_Org_ID))              AS revenue_net
                               FROM line_values lv
                               GROUP BY lv.day)

        SELECT dr.day,
               p.bpartner_name,
               p.bpartner_group_name,
               dr.revenue_net AS delivery_revenue_net,
               v_cursymbol    AS cursymbol
        FROM daily_revenue dr
                 CROSS JOIN params p
        ORDER BY dr.day DESC;

END;
$$
    LANGUAGE plpgsql
    STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.Docs_Daily_Delivery_Revenue_Report(numeric, numeric, timestamp, timestamp) IS
    'Returns a daily delivery-revenue report ("Tagesumsatz auf Basis Lieferung"), aggregated by M_InOut.MovementDate.
    For each completed sales delivery line (M_InOut IsSOTrx=Y, DocStatus CO/CL), the line''s net value is:
      invoiced portion (sum of matched, active C_InvoiceLine.LineNetAmt/QtyInvoiced via
        C_InvoiceLine.M_InOutLine_ID, for invoices with DocStatus CO/CL only -- reversed/drafted invoices
        are excluded)
      + open portion (C_Invoice_Candidate.PriceActual x (C_Invoice_Candidate.QtyDelivered - invoiced qty),
        rounded to currency precision; the IC-level QtyDelivered is used because each ordinary sales delivery
        line gets a dedicated 1:1 invoice candidate and the IC-level column carries the correct negative sign
        for returns). The open qty is derived from C_InvoiceLine.QtyInvoiced (settled synchronously at invoice
        creation), not from C_Invoice_Candidate.QtyInvoiced (only brought current by a later recompute pass).
    Each delivered unit is counted exactly once (invoiced-qty + open-qty = delivered-qty); the two portions are
    rounded independently at their own grain, so the line total may differ by a cent from a theoretical
    round(price x delivered-qty) when the invoiced portion reflects a non-divisible price (see CONCEPT.md).
    A customer return is its own M_InOutLine (negative MovementQty) carrying its own negated invoice candidate,
    so it nets its own day down without touching the original delivery''s valuation.
    Amounts are converted to accounting currency via currencyBase().
    Parameters:
      p_bpartner_id – filter by business partner
      p_bp_group_id – filter by partner group
      p_date_from   – start of date range, inclusive (by MovementDate)
      p_date_to     – end of date range, inclusive (by MovementDate)'
;
