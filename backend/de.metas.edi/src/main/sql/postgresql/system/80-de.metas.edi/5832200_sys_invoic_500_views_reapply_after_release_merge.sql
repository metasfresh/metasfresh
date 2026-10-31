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

-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_invoic_500_incl_packaging_v_view.sql
--             backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_invoic_500_v_view.sql
-- Re-applies the INVOIC line views after the merge of intensive_care_release into new_dawn_uat.
-- 5832160 (from the release line) redefines edi_cctop_invoic_500_v as a stand-alone aggregate that still contains the
-- packaging-material lines and runs after 5829780, so on a database that applies both it would undo the
-- packaging split. The two guards of 5832160 (migration-marker ProductDescription, GS1 variable-measure EAN_CU prefix)
-- therefore go into edi_cctop_invoic_500_incl_packaging_v here, and edi_cctop_invoic_500_v is restored as its
-- packaging-free subset (as 5829780 defines it).
-- Both views have dependent views, so the changes go through db_alter_view.

DROP VIEW IF EXISTS edi_cctop_invoic_500_incl_packaging_v$new;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_incl_packaging_v$new AS
SELECT il.ispackagingmaterial                                                  AS IsPackagingMaterial,
       SUM(il.qtyEntered)                                                        AS QtyInvoiced,
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
               CASE WHEN asi_data.productdescription LIKE '[Migrated from C_BPartner_Product %]' THEN NULL ELSE NULLIF(asi_data.productdescription, '') END,
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
                NULLIF(CASE
                           WHEN LEFT(BTRIM(asi_data.ean_cu), 2) IN ('02', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29') THEN NULL /* GS1 restricted circulation / variable-measure prefix: not a GTIN */
                           ELSE REGEXP_REPLACE(asi_data.ean_cu, '\s+$', '')
                       END, ''),
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
         il.C_UOM_BPartner_ID, il.externalids, ol.externalseqno
;

SELECT db_alter_view(
    'edi_cctop_invoic_500_incl_packaging_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('edi_cctop_invoic_500_incl_packaging_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_invoic_500_incl_packaging_v$new;

-- db_alter_view recreates the view from its definition only, so re-apply the view comment.
COMMENT ON VIEW edi_cctop_invoic_500_incl_packaging_v IS 'All INVOIC line items incl. packaging-material lines (IsPackagingMaterial=Y). Source of the INVOIC JSON lines; edi_cctop_invoic_500_v is the packaging-free subset.
Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;

DROP VIEW IF EXISTS edi_cctop_invoic_500_v$new;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_v$new AS
SELECT qtyinvoiced, eancom_uom, qtyinvoicedinordereduom, eancom_ordered_uom, edi_cctop_invoic_500_v_id, linenetamt,
         line, c_invoice_id, edi_cctop_invoic_v_id, priceactual, pricelist, discount, invoicableqtybasedon, upc_cu,
         ean_cu, value, product_deposittype, customerproductno, name, name2, rate, eancom_price_uom, taxfree,
         istaxexempt, iso_code, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, leergut,
         productdescription, orderline, orderporeference, c_orderline_id, taxamtinfo, gtin, ean_tu, upc_tu,
         buyer_gtin_tu, buyer_gtin_cu, buyer_ean_cu, supplier_gtin_cu, qtyenteredinbpartneruom, c_uom_bpartner_id,
         externalid, externalseqno
FROM edi_cctop_invoic_500_incl_packaging_v
WHERE IsPackagingMaterial = 'N'
ORDER BY orderline
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
Packaging-material lines are excluded here (legacy XML export, historical invoices JSON); the INVOIC JSON reads edi_cctop_invoic_500_incl_packaging_v.
'
;
