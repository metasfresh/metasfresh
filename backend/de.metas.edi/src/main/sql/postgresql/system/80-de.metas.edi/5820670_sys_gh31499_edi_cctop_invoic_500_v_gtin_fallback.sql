/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_invoic_500_v_view.sql
-- INVOIC export: harmonize the CU/TU GTIN fallback with DESADV and EPCIS.
--   Buyer_GTIN_CU        : asi_data.gtin -> asi_data.ean_cu -> asi_data.ean13_productcode -> product.gtin
--   GTIN / Buyer_GTIN_TU : packing-instruction gtin -> packing-instruction ean_tu
-- Supplier_GTIN_CU stays the supplier's own product GTIN and deliberately does NOT fall back to the
-- buyer-scoped asi_data, since those identifiers mean different things to the receiver.
--
-- edi_cctop_invoic_500_v has dependent views (c_invoice_export_edi_invoic_json_v,
-- historical_invoices_json_v), so the change goes through db_alter_view, which drops and recreates
-- them in dependency order. A bare DROP VIEW fails on the dependents; a bare CREATE OR REPLACE
-- cannot change the column list.

DROP VIEW IF EXISTS edi_cctop_invoic_500_v$new;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_v$new AS
SELECT SUM(il.qtyEntered)                                                        AS QtyInvoiced,
       CASE
           WHEN u.x12de355 = 'TU' THEN 'PCE'
                                  ELSE u.x12de355
       END                                                                       AS eancom_uom, /* C_InvoiceLine's UOM */
       SUM(CASE
               WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                         ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
           END)                                                                  AS QtyInvoicedInOrderedUOM,
       u_ordered.x12de355                                                        AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it's needed (anymore) */
       MIN(il.c_invoiceline_id)                                                  AS edi_cctop_invoic_500_v_id,
       SUM(il.linenetamt)                                                        AS linenetamt,
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
               NULLIF(asi_data.productdescription, ''),
               NULLIF(asi_data.productname, ''),
               NULLIF(p.description, ''),
               p.name)::character varying                                        AS productdescription,
       COALESCE(ol.line, il.line)                                                AS orderline,
       COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
       il.c_orderline_id,
       SUM(il.taxamtinfo)                                                        AS taxamtinfo,
       COALESCE(NULLIF(REGEXP_REPLACE(pip.GTIN, '\s+$', ''), ''),
                REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')
       )                                                                         AS GTIN,   -- Deprecated: superseded by buyer_gtin_tu
       REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')                                    AS EAN_TU,
       REGEXP_REPLACE(pip.UPC, '\s+$', '')                                       AS UPC_TU,
       COALESCE(NULLIF(REGEXP_REPLACE(pip.GTIN, '\s+$', ''), ''),
                REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')
       )                                                                         AS Buyer_GTIN_TU,
       COALESCE(NULLIF(REGEXP_REPLACE(asi_data.gtin, '\s+$', ''), ''),
                NULLIF(REGEXP_REPLACE(asi_data.ean_cu, '\s+$', ''), ''),
                NULLIF(REGEXP_REPLACE(asi_data.ean13_productcode, '\s+$', ''), ''),
                REGEXP_REPLACE(p.gtin, '\s+$', ''))                              AS Buyer_GTIN_CU,
       REGEXP_REPLACE(asi_data.EAN_CU, '\s+$', '')                               AS Buyer_EAN_CU,
       REGEXP_REPLACE(p.GTIN, '\s+$', '')                                        AS Supplier_GTIN_CU, /* the SUPPLIER's own GTIN — deliberately NOT the buyer-scoped asi_data */
       SUM(il.QtyEnteredInBPartnerUOM)                                           AS qtyEnteredInBPartnerUOM,
       il.C_UOM_BPartner_ID                                                      AS C_UOM_BPartner_ID,
       il.externalids                                                            AS ExternalId,
       ol.externalseqno                                                          AS externalSeqNo
FROM c_invoiceline il
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
WHERE il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
  AND il.ispackagingmaterial = 'N'
GROUP BY il.c_invoice_id,
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
         (COALESCE(NULLIF(asi_data.productdescription, ''), NULLIF(asi_data.productname, ''), NULLIF(p.description, ''), p.name)),
         (COALESCE(NULLIF(o.poreference, ''), i.poreference)),
         (COALESCE(ol.line, il.line)),
         il.c_orderline_id,
         pip.UPC, pip.GTIN, pip.EAN_TU, asi_data.GTIN, asi_data.EAN_CU, p.GTIN,
         il.C_UOM_BPartner_ID, il.externalids, ol.externalseqno
ORDER BY COALESCE(ol.line, il.line)
;

SELECT db_alter_view(
    'edi_cctop_invoic_500_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('edi_cctop_invoic_500_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_invoic_500_v$new;

-- db_alter_view recreates the view from its definition only, so re-apply the view comment.
COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;
