create or replace view rv_c_orderline_overview
            (c_orderline_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, c_order_id,
             line, dateordered, datepromised, datedelivered, dateinvoiced, description, m_product_id, c_uom_id,
             m_warehouse_id, qtyordered, qtyreserved, qtydelivered, qtyinvoiced, m_shipper_id, c_currency_id, pricelist,
             priceactual, c_tax_id, c_bpartner_id, freightamt, c_charge_id, c_bpartner_location_id, linenetamt,
             pricelimit, discount, s_resourceassignment_id, ref_orderline_id, m_attributesetinstance_id, isdescription,
             processed, priceentered, qtyentered, c_project_id, pricecost, qtylostsales, c_projectphase_id,
             c_projecttask_id, rrstartdate, rramt, c_campaign_id, c_activity_id, user1_id, user2_id, ad_orgtrx_id,
             link_orderline_id, m_promotion_id, pp_cost_collector_id, docstatus, issotrx, c_doctype_id, documentno,
             isinvoiced, isdelivered, ispurchased, duetype, daysdue, ispaid)
as
SELECT ol.c_orderline_id,
       ol.ad_client_id,
       ol.ad_org_id,
       ol.isactive,
       ol.created,
       ol.createdby,
       ol.updated,
       ol.updatedby,
       ol.c_order_id,
       ol.line,
       ol.dateordered,
       ol.datepromised,
       ol.datedelivered,
       ol.dateinvoiced,
       ol.description,
       ol.m_product_id,
       ol.c_uom_id,
       ol.m_warehouse_id,
       ol.qtyordered,
       ol.qtyreserved,
       ol.qtydelivered,
       ol.qtyinvoiced,
       ol.m_shipper_id,
       ol.c_currency_id,
       ol.pricelist,
       ol.priceactual,
       ol.c_tax_id,
       ol.c_bpartner_id,
       ol.freightamt,
       ol.c_charge_id,
       ol.c_bpartner_location_id,
       ol.linenetamt,
       ol.pricelimit,
       ol.discount,
       ol.s_resourceassignment_id,
       ol.ref_orderline_id,
       ol.m_attributesetinstance_id,
       ol.isdescription,
       ol.processed,
       ol.priceentered,
       ol.qtyentered,
       ol.c_project_id,
       ol.pricecost,
       ol.qtylostsales,
       ol.c_projectphase_id,
       ol.c_projecttask_id,
       ol.rrstartdate,
       ol.rramt,
       ol.c_campaign_id,
       ol.c_activity_id,
       ol.user1_id,
       ol.user2_id,
       ol.ad_orgtrx_id,
       po_line_alloc.c_po_orderline_id as link_orderline_id,
       ol.m_promotion_id,
       ol.pp_cost_collector_id,
       o.docstatus,
       o.issotrx,
       o.c_doctype_id,
       o.documentno,
       CASE
           WHEN ol.qtyordered <= ol.qtyinvoiced THEN 'Y'::text
           ELSE 'N'::text
           END                                 AS isinvoiced,
       CASE
           WHEN ol.qtyordered <= ol.qtydelivered THEN 'Y'::text
           ELSE 'N'::text
           END                                 AS isdelivered,
       CASE
           WHEN o.issotrx = 'Y'::bpchar AND po_line_alloc.c_po_orderline_id IS NOT NULL THEN 'Y'::text
           ELSE 'N'::text
           END                                 AS ispurchased,
       CASE
           WHEN ol.qtyordered <= ol.qtydelivered AND o.issotrx = 'Y'::bpchar AND po_line_alloc.c_po_orderline_id IS NOT NULL
               THEN '9'::text
           WHEN o.docstatus <> 'CO'::bpchar THEN NULL::text
           WHEN trunc(ol.datepromised, 'DD'::character varying) < trunc(getdate(), 'DD'::character varying) THEN '3'::text
           WHEN trunc(ol.datepromised, 'DD'::character varying) = trunc(getdate(), 'DD'::character varying) THEN '5'::text
           WHEN trunc(ol.datepromised, 'DD'::character varying) > trunc(getdate(), 'DD'::character varying) THEN '7'::text
           ELSE NULL::text
           END                                 AS duetype,
       daysbetween(getdate(), ol.datepromised) AS daysdue,
       CASE
           WHEN orderprepaid(o.c_order_id, o.c_currency_id) >= o.grandtotal THEN 'Y'::text
           ELSE 'N'::text
           END                                 AS ispaid
FROM c_orderline ol
         JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_po_orderline_alloc po_line_alloc on ol.c_orderline_id = po_line_alloc.c_so_orderline_id and o.isSoTrx = 'Y';

alter table rv_c_orderline_overview
    owner to postgres;
