/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- View: public.edi_invoic_json_line_shipment_v
-- Has dependents: edi_invoic_json_line_v: change it only via db_alter_view in a new migration script.

CREATE OR REPLACE VIEW edi_invoic_json_line_shipment_v AS
SELECT il.c_invoice_id,
       il.c_invoiceline_id,
       parts.m_inout_id,
       parts.shipment_documentno,
       /* every part but the last gets its rounded share, the last one the remainder, so the parts add up exactly */
       CASE
           WHEN parts.rn = parts.cnt
               THEN il.qtyinvoiced - COALESCE(SUM(ROUND(il.qtyinvoiced * parts.share, u_stock.stdprecision::integer)) OVER w_preceding, 0)
               ELSE ROUND(il.qtyinvoiced * parts.share, u_stock.stdprecision::integer)
       END AS qtyinvoiced,
       CASE
           WHEN parts.rn = parts.cnt
               THEN il.qtyentered - COALESCE(SUM(ROUND(il.qtyentered * parts.share, u_entered.stdprecision::integer)) OVER w_preceding, 0)
               ELSE ROUND(il.qtyentered * parts.share, u_entered.stdprecision::integer)
       END AS qtyentered,
       CASE
           WHEN parts.rn = parts.cnt
               THEN il.qtyenteredinbpartneruom - COALESCE(SUM(ROUND(il.qtyenteredinbpartneruom * parts.share, COALESCE(u_bpartner.stdprecision, u_entered.stdprecision)::integer)) OVER w_preceding, 0)
               ELSE ROUND(il.qtyenteredinbpartneruom * parts.share, COALESCE(u_bpartner.stdprecision, u_entered.stdprecision)::integer)
       END AS qtyenteredinbpartneruom,
       CASE
           WHEN parts.rn = parts.cnt
               THEN il.linenetamt - COALESCE(SUM(ROUND(il.linenetamt * parts.share, cur.stdprecision::integer)) OVER w_preceding, 0)
               ELSE ROUND(il.linenetamt * parts.share, cur.stdprecision::integer)
       END AS linenetamt,
       CASE
           WHEN parts.rn = parts.cnt
               THEN il.taxamtinfo - COALESCE(SUM(ROUND(il.taxamtinfo * parts.share, cur.stdprecision::integer)) OVER w_preceding, 0)
               ELSE ROUND(il.taxamtinfo * parts.share, cur.stdprecision::integer)
       END AS taxamtinfo
FROM c_invoiceline il
         JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         JOIN c_currency cur ON cur.c_currency_id = i.c_currency_id
         JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN c_uom u_stock ON u_stock.c_uom_id = p.c_uom_id
         LEFT JOIN c_uom u_entered ON u_entered.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_bpartner ON u_bpartner.c_uom_id = il.c_uom_bpartner_id
    /* material matches of this line, summed per shipment; only used when the line has no own shipment line link */
         CROSS JOIN LATERAL (
    SELECT COUNT(*)      AS groups_count,
           SUM(g.qty_abs) AS qty_abs_sum
    FROM (SELECT ABS(SUM(mi.qty)) AS qty_abs
          FROM m_matchinv mi
          WHERE mi.c_invoiceline_id = il.c_invoiceline_id
            AND mi.isactive = 'Y'
            AND mi.type = 'M'
          GROUP BY mi.m_inout_id
          HAVING SUM(mi.qty) <> 0) g
    ) mtot
    /* the one completed/closed shipment found via the invoice candidates' shipment links, if it covers the whole line quantity */
         LEFT JOIN LATERAL (
    SELECT MIN(s.m_inout_id)  AS m_inout_id,
           MIN(s.documentno) AS documentno
    FROM (SELECT io.m_inout_id, io.documentno, SUM(x.qtydelivered) AS qtydelivered
          FROM (SELECT icio.m_inoutline_id, MAX(icio.qtydelivered) AS qtydelivered /* one per shipment line, even if several invoice candidates link it */
                FROM c_invoice_line_alloc ila
                         JOIN c_invoicecandidate_inoutline icio ON icio.c_invoice_candidate_id = ila.c_invoice_candidate_id AND icio.isactive = 'Y'
                WHERE ila.c_invoiceline_id = il.c_invoiceline_id
                  AND ila.isactive = 'Y'
                GROUP BY icio.m_inoutline_id) x
                   JOIN m_inoutline iol ON iol.m_inoutline_id = x.m_inoutline_id
                   JOIN m_inout io ON io.m_inout_id = iol.m_inout_id AND io.docstatus IN ('CO', 'CL')
          GROUP BY io.m_inout_id, io.documentno) s
    HAVING COUNT(*) = 1
       AND ABS(MIN(s.qtydelivered)) >= ABS(il.qtyinvoiced)
    ) iclink ON il.m_inoutline_id IS NULL AND (il.qtyinvoiced = 0 OR mtot.groups_count = 0)
         CROSS JOIN LATERAL (
    SELECT pp.m_inout_id,
           pp.shipment_documentno,
           pp.share,
           ROW_NUMBER() OVER (ORDER BY pp.shipment_documentno NULLS LAST, pp.m_inout_id NULLS LAST) AS rn,
           COUNT(*) OVER ()                                                                       AS cnt
    FROM (
             /* 1. own shipment line link: the whole line; the number only if that shipment is completed/closed */
             SELECT iol.m_inout_id,
                    CASE WHEN io.docstatus IN ('CO', 'CL') THEN io.documentno END AS shipment_documentno,
                    1::numeric                                                    AS share
             FROM (SELECT 1) one
                      LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = il.m_inoutline_id
                      LEFT JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
             WHERE il.m_inoutline_id IS NOT NULL

             UNION ALL

             /* 2. material matches: one part per shipment, share on absolute values; the sign comes from the invoice line */
             SELECT mi.m_inout_id,
                    CASE WHEN io.docstatus IN ('CO', 'CL') THEN io.documentno END                AS shipment_documentno,
                    ABS(SUM(mi.qty)) / GREATEST(mtot.qty_abs_sum, ABS(il.qtyinvoiced))            AS share
             FROM m_matchinv mi
                      LEFT JOIN m_inout io ON io.m_inout_id = mi.m_inout_id
             WHERE il.m_inoutline_id IS NULL
               AND il.qtyinvoiced <> 0
               AND mi.c_invoiceline_id = il.c_invoiceline_id
               AND mi.isactive = 'Y'
               AND mi.type = 'M'
             GROUP BY mi.m_inout_id, io.docstatus, io.documentno
             HAVING SUM(mi.qty) <> 0

             UNION ALL

             /* 2. unmatched remainder (e.g. a matched shipment was reversed): no shipment */
             SELECT NULL::numeric, NULL::varchar, (ABS(il.qtyinvoiced) - mtot.qty_abs_sum) / ABS(il.qtyinvoiced)
             WHERE il.m_inoutline_id IS NULL
               AND il.qtyinvoiced <> 0
               AND mtot.groups_count > 0
               AND mtot.qty_abs_sum < ABS(il.qtyinvoiced)

             UNION ALL

             /* 3. neither own link nor material matches: the whole line; shipment only via the invoice candidates' shipment links */
             SELECT iclink.m_inout_id, iclink.documentno, 1::numeric
             WHERE il.m_inoutline_id IS NULL
               AND (il.qtyinvoiced = 0 OR mtot.groups_count = 0)
         ) pp
    ) parts
WHERE il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
WINDOW w_preceding AS (PARTITION BY il.c_invoice_id, il.c_invoiceline_id ORDER BY parts.rn ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING)
;

COMMENT ON VIEW edi_invoic_json_line_shipment_v IS 'INVOIC JSON: allocates each invoice line (active, with product, QtyEntered <> 0) to its delivery note(s); one row per invoice line and shipment.
1. C_InvoiceLine.M_InOutLine_ID set: one row with the line''s own values; Shipment_DocumentNo only if that shipment is completed/closed, else NULL.
2. else active material M_MatchInv rows: one row per shipment with the matched share (absolute values, sign from the invoice line); an unmatched remainder gets one more row without shipment; the last row takes the rounding remainder, so the rows add up to the line.
3. else one row with the line''s own values; the shipment only if exactly one completed/closed shipment is linked via C_Invoice_Line_Alloc -> C_InvoiceCandidate_InOutLine and its linked quantity covers the line''s whole quantity, else NULL.
Never the invoice header''s shipment number.'
;
