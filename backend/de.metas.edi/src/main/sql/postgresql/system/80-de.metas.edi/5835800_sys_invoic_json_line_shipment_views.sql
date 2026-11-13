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

-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_invoic_json_line_shipment_v.sql
-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_invoic_json_line_v.sql
-- INVOIC JSON: two new views (no dependents yet, hence plain CREATE OR REPLACE VIEW):
-- edi_invoic_json_line_shipment_v allocates every invoice line to its delivery note(s) (one row per line and shipment, split lines add up exactly);
-- edi_invoic_json_line_v is the JSON line source built on it, with m_inout_id, Shipment_DocumentNo, QtyCUsPerTU and QtyCUsPerTU_UOM.
-- The allocation view is created first, the line view reads it.

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


CREATE OR REPLACE VIEW edi_invoic_json_line_v AS
SELECT il.ispackagingmaterial                                                  AS IsPackagingMaterial,
       SUM(a.qtyEntered)                                                         AS QtyInvoiced,
       CASE
           WHEN u.x12de355 = 'TU' THEN 'PCE'
                                  ELSE u.x12de355
       END                                                                       AS eancom_uom, /* C_InvoiceLine's UOM */
       SUM(CASE
               WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(a.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                         ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, a.QtyInvoiced)
           END)                                                                  AS QtyInvoicedInOrderedUOM,
       u_ordered.x12de355                                                        AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it's needed (anymore) */
       MIN(il.c_invoiceline_id)                                                  AS edi_cctop_invoic_500_v_id,
       SUM(a.linenetamt)                                                         AS linenetamt,
       MIN(il.line)                                                              AS line,
       il.c_invoice_id,
       il.c_invoice_id                                                           AS edi_cctop_invoic_v_id,
       il.priceactual,
       il.pricelist,
       il.discount,
       ol.invoicableqtybasedon,
       REGEXP_REPLACE(asi_data.UPC, '\s+$', '')                                  AS UPC_CU,
       REGEXP_REPLACE(asi_data.EAN_CU, '\s+$', '')                               AS EAN_CU, -- Deprecated: superseded by buyer_ean_cu
       REGEXP_REPLACE(p.value, '\s+$', '')                                       AS Value,
       p.DepositType                                                             AS Product_DepositType,
       REGEXP_REPLACE(asi_data.productno, '\s+$', '')                            AS CustomerProductNo,
       SUBSTR(p.name, 1, 35)                                                     AS name,
       SUBSTR(p.name, 36, 70)                                                    AS name2,
       t.rate,
       CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
           WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                              ELSE COALESCE(u_price.x12de355, u.x12de355)
       END                                                                       AS eancom_price_uom /* C_InvoiceLine's Price-UOM */,
       CASE
           WHEN t.rate = 0 THEN 'Y'
                           ELSE ''
       END                                                                       AS taxfree,
       t.IsTaxExempt,
       c.iso_code,
       il.ad_client_id,
       il.ad_org_id,
       MIN(il.created)                                                           AS created,
       MIN(il.createdby)::numeric(10, 0)                                         AS createdby,
       MAX(il.updated)                                                           AS updated,
       MAX(il.updatedby)::numeric(10, 0)                                         AS updatedby,
       il.isactive,
       CASE pc.value
           WHEN 'Leergut' THEN 'P'
                          ELSE ''
       END                                                                       AS leergut,
       COALESCE(
               CASE WHEN asi_data.productdescription LIKE '[Migrated from C_BPartner_Product %]' THEN NULL ELSE NULLIF(asi_data.productdescription, '') END,
               NULLIF(asi_data.productname, ''),
               NULLIF(p.description, ''),
               p.name)::character varying                                        AS productdescription,
       COALESCE(ol.line, il.line)                                                AS orderline,
       COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
       il.c_orderline_id,
       SUM(a.taxamtinfo)                                                         AS taxamtinfo,
       COALESCE(NULLIF(REGEXP_REPLACE(pip.GTIN, '\s+$', ''), ''),
                REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')
       )                                                                         AS GTIN,   -- Deprecated: superseded by buyer_gtin_tu
       REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')                                    AS EAN_TU,
       REGEXP_REPLACE(pip.UPC, '\s+$', '')                                       AS UPC_TU,
       COALESCE(NULLIF(REGEXP_REPLACE(pip.GTIN, '\s+$', ''), ''),
                REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')
       )                                                                         AS Buyer_GTIN_TU,
       COALESCE(NULLIF(REGEXP_REPLACE(asi_data.gtin, '\s+$', ''), ''),
                NULLIF(CASE
                           WHEN LEFT(BTRIM(asi_data.ean_cu), 2) IN ('02', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29') THEN NULL /* GS1 restricted circulation / variable-measure prefix: not a GTIN */
                           ELSE REGEXP_REPLACE(asi_data.ean_cu, '\s+$', '')
                       END, ''),
                NULLIF(REGEXP_REPLACE(asi_data.ean13_productcode, '\s+$', ''), ''),
                REGEXP_REPLACE(p.gtin, '\s+$', ''))                              AS Buyer_GTIN_CU,
       REGEXP_REPLACE(asi_data.EAN_CU, '\s+$', '')                               AS Buyer_EAN_CU,
       REGEXP_REPLACE(p.GTIN, '\s+$', '')                                        AS Supplier_GTIN_CU, /* the SUPPLIER's own GTIN — deliberately NOT the buyer-scoped asi_data */
       SUM(a.QtyEnteredInBPartnerUOM)                                            AS qtyEnteredInBPartnerUOM,
       il.C_UOM_BPartner_ID                                                      AS C_UOM_BPartner_ID,
       il.externalids                                                            AS ExternalId,
       ol.externalseqno                                                          AS externalSeqNo,
       a.m_inout_id,
       a.shipment_documentno                                                     AS Shipment_DocumentNo,
       CASE
           WHEN il.IsPackagingMaterial = 'Y' THEN 1
           WHEN ol.BPartner_QtyItemCapacity > 0 THEN ol.BPartner_QtyItemCapacity
           WHEN pip.IsInfiniteCapacity = 'N' AND pip.Qty > 0 THEN pip.Qty
       END                                                                       AS QtyCUsPerTU,
       CASE
           WHEN il.IsPackagingMaterial = 'Y' THEN (CASE
                                                       WHEN u.x12de355 = 'TU' THEN 'PCE'
                                                                              ELSE u.x12de355
                                                   END)
           WHEN ol.BPartner_QtyItemCapacity > 0 THEN u_product.x12de355
           WHEN pip.IsInfiniteCapacity = 'N' AND pip.Qty > 0 THEN COALESCE(u_pip.x12de355, u_product.x12de355)
       END                                                                       AS QtyCUsPerTU_UOM
FROM c_invoiceline il
         JOIN edi_invoic_json_line_shipment_v a ON a.c_invoiceline_id = il.c_invoiceline_id AND a.c_invoice_id = il.c_invoice_id /* lets a c_invoice_id filter reach the allocation view */
         LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
         LEFT JOIN m_hu_pi_item_product pip ON COALESCE(ol.m_hu_pi_item_product_id, il.m_hu_pi_item_product_id) = pip.m_hu_pi_item_product_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
         LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         -- ASI-aware product data lookup (M_Product_ASI_Data with content-based ASI subset matching)
         LEFT JOIN LATERAL (
             SELECT gtin, ean_cu, ean13_productcode, upc, productno, productdescription, productname
             FROM m_product_asi_data
             WHERE isactive = 'Y'
               AND m_product_id = il.m_product_id
               AND (c_bpartner_id IS NULL OR c_bpartner_id = i.c_bpartner_id)
               AND IsASIAttributesKeySubset(m_attributesetinstance_id, il.m_attributesetinstance_id)
             ORDER BY seqno
             LIMIT 1
         ) asi_data ON TRUE
         LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
         LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
         LEFT JOIN c_uom u_product ON u_product.c_uom_id = p.c_uom_id
         LEFT JOIN c_uom u_pip ON u_pip.c_uom_id = pip.c_uom_id
WHERE il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
GROUP BY il.c_invoice_id,
         il.ispackagingmaterial,
         il.priceactual,
         il.pricelist,
         il.discount,
         ol.InvoicableQtyBasedOn,
         asi_data.UPC,
         asi_data.EAN_CU,
         asi_data.ean13_productcode,
         p.value,
         p.DepositType,
         asi_data.productno,
         (SUBSTR(p.name, 1, 35)),
         (SUBSTR(p.name, 36, 70)),
         t.rate,
         t.IsTaxExempt,
         (CASE
              WHEN u.x12de355 = 'TU' THEN 'PCE'
                                     ELSE u.x12de355
          END),
         (CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
              WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                                 ELSE COALESCE(u_price.x12de355, u.x12de355)
          END),
         u_ordered.x12de355,
         (CASE
              WHEN t.rate = 0 THEN 'Y'
                              ELSE ''
          END),
         c.iso_code,
         il.ad_client_id,
         il.ad_org_id,
         il.isactive,
         (CASE pc.value
              WHEN 'Leergut' THEN 'P'
                             ELSE ''
          END),
         (COALESCE(CASE WHEN asi_data.productdescription LIKE '[Migrated from C_BPartner_Product %]' THEN NULL ELSE NULLIF(asi_data.productdescription, '') END, NULLIF(asi_data.productname, ''), NULLIF(p.description, ''), p.name)),
         (COALESCE(NULLIF(o.poreference, ''), i.poreference)),
         (COALESCE(ol.line, il.line)),
         il.c_orderline_id,
         pip.UPC, pip.GTIN, pip.EAN_TU, asi_data.GTIN, asi_data.EAN_CU, p.GTIN,
         il.C_UOM_BPartner_ID, il.externalids, ol.externalseqno,
         a.m_inout_id,
         a.shipment_documentno,
         (CASE
              WHEN il.IsPackagingMaterial = 'Y' THEN 1
              WHEN ol.BPartner_QtyItemCapacity > 0 THEN ol.BPartner_QtyItemCapacity
              WHEN pip.IsInfiniteCapacity = 'N' AND pip.Qty > 0 THEN pip.Qty
          END),
         (CASE
              WHEN il.IsPackagingMaterial = 'Y' THEN (CASE
                                                          WHEN u.x12de355 = 'TU' THEN 'PCE'
                                                                                 ELSE u.x12de355
                                                      END)
              WHEN ol.BPartner_QtyItemCapacity > 0 THEN u_product.x12de355
              WHEN pip.IsInfiniteCapacity = 'N' AND pip.Qty > 0 THEN COALESCE(u_pip.x12de355, u_product.x12de355)
          END)
;

COMMENT ON VIEW edi_invoic_json_line_v IS 'INVOIC JSON line source: the lines of edi_cctop_invoic_500_incl_packaging_v, but one row per delivery note (quantities and amounts from edi_invoic_json_line_shipment_v; lines of different shipments are never grouped together).
Shipment_DocumentNo: the line''s delivery note as allocated by edi_invoic_json_line_shipment_v, NULL if not unambiguous (never the invoice header''s).
QtyCUsPerTU / QtyCUsPerTU_UOM: 1 in the line''s unit on a packaging-material line; else the ordered CUs per TU (C_OrderLine.BPartner_QtyItemCapacity) in the product''s unit; else a finite packing instruction''s quantity in its unit (fallback: the product''s unit); else NULL.
Notes (inherited):
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;
