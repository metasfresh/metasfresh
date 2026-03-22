/*
 * #%L
 * de.metas.business
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


DROP VIEW IF EXISTS C_Order_M_InOut_C_Invoice_Overview_V
;

CREATE OR REPLACE VIEW C_Order_M_InOut_C_Invoice_Overview_V AS
SELECT ROW_NUMBER() OVER (ORDER BY ad_Table_id, head_id, line_id) AS C_Order_M_InOut_C_Invoice_Overview_V_ID,
       doc.ad_Table_id,
       doc.Record_ID,
       doc.issotrx,
       doc.c_doctype_id,
       dt.docbasetype,
       doc.documentno,
       doc.processed,
       doc.docstatus,
       doc.date,
       doc.c_bpartner_id,
       doc.c_bpartner_location_id,
       doc.m_product_id,
       p.c_uom_id,
       doc.qty,
       doc.linenetamt,
       COALESCE(stock.qtyonhand, 0)                               AS current_qty_sum,
       doc.ad_client_id,
       doc.ad_org_id,
       doc.created,
       doc.createdby,
       doc.isactive,
       doc.posted
FROM (SELECT get_table_id('C_Order')                                   AS ad_Table_id,
             o.c_order_id                                              AS Record_ID,
             o.c_order_id                                              AS head_id,
             ol.c_orderline_id                                         AS line_id,
             ol.ad_client_id,
             ol.ad_org_id,
             ol.created,
             ol.createdby,
             GREATEST(ol.updated, o.updated)                           AS updated,
             CASE
                 WHEN ol.updated > o.updated THEN ol.updatedby
                                             ELSE o.updatedby
             END                                                       AS updatedby,
             ol.isactive,
             o.issotrx,
             COALESCE(NULLIF(o.c_doctype_id, 0), o.c_doctypetarget_id) AS c_doctype_id,
             o.documentno,
             o.processed,
             o.docstatus,
             o.dateordered                                             AS date,
             o.c_bpartner_id,
             o.c_bpartner_location_id,
             o.m_warehouse_id,
             o.posted,
             ol.m_product_id,
             ol.qtyordered                                             AS qty,
             ol.m_hu_pi_item_product_id,
             ol.priceactual,
             ol.linenetamt
      FROM c_order o
               JOIN c_orderline ol ON ol.c_orderline_id = o.c_order_id
      UNION

      SELECT get_table_id('M_InOut')           AS ad_Table_id,
             io.m_inout_id                     AS Record_ID,
             io.m_inout_id                     AS head_id,
             iol.m_inoutline_id                AS line_id,
             iol.ad_client_id,
             iol.ad_org_id,
             iol.created,
             iol.createdby,

             GREATEST(iol.updated, io.updated) AS updated,
             CASE
                 WHEN iol.updated > io.updated THEN iol.updatedby
                                               ELSE io.updatedby
             END                               AS updatedby,
             iol.isactive,
             io.issotrx,
             io.c_doctype_id,
             io.documentno,
             io.processed,
             io.docstatus,
             io.movementdate,
             io.c_bpartner_id,
             io.c_bpartner_location_id,
             io.m_warehouse_id,
             io.posted,
             iol.m_product_id,
             iol.movementqty,
             iol.m_hu_pi_item_product_id,
             NULL,
             NULL
      FROM m_inout io
               JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id

      UNION

      SELECT get_table_id('C_Invoice')                                 AS ad_Table_id,
             i.c_invoice_id                                            AS Record_ID,
             i.c_invoice_id                                              AS head_id,
             il.c_invoiceline_id                                       AS line_id,
             il.ad_client_id,
             il.ad_org_id,
             il.created,
             il.createdby,

             GREATEST(il.updated, i.updated)                           AS updated,
             CASE
                 WHEN il.updated > i.updated THEN il.updatedby
                                             ELSE i.updatedby
             END                                                       AS updatedby,
             il.isactive,
             i.issotrx,
             COALESCE(NULLIF(i.c_doctype_id, 0), i.c_doctypetarget_id) AS c_doctype_id,
             i.documentno,
             i.processed,
             i.docstatus,
             i.dateinvoiced,
             i.c_bpartner_id,
             i.c_bpartner_location_id,
             i.m_warehouse_id,
             i.posted,
             il.m_product_id,
             il.qtyinvoiced,
             il.m_hu_pi_item_product_id,
             il.priceactual,
             il.linenetamt
      FROM c_invoice i
               JOIN c_invoiceline il
                    ON il.c_invoice_id = i.c_invoice_id) doc
         LEFT JOIN c_doctype dt ON dt.c_doctype_id = doc.c_doctype_id
         JOIN m_product p ON p.m_product_id = doc.m_product_id
         LEFT JOIN (SELECT s.m_product_id, s.m_warehouse_id, SUM(s.qtyonhand) AS qtyonhand
                    FROM md_stock s
                    WHERE s.isactive = 'Y'
                    GROUP BY s.m_product_id, s.m_warehouse_id) stock ON doc.m_product_id = stock.m_product_id AND doc.m_warehouse_id = stock.m_warehouse_id
;
