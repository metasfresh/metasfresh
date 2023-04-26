DROP VIEW RV_DD_OrderDetail
;

CREATE OR REPLACE VIEW RV_DD_OrderDetail AS
SELECT l.ad_client_id,
       l.ad_org_id,
       l.isactive,
       l.created,
       l.createdby,
       l.updated,
       l.updatedby,
       o.dd_order_id,
       o.c_order_id,
       o.docstatus,
       o.docaction,
       o.c_doctype_id,
       o.isapproved,
       o.salesrep_id,
       o.isdropship,
       o.c_bpartner_id,
       bp.c_bp_group_id,
       o.ad_user_id,
       o.poreference,
       o.issotrx,
       l.c_campaign_id,
       l.c_project_id,
       l.c_activity_id,
       l.dd_orderline_id,
       l.dateordered,
       l.datepromised,
       l.m_product_id,
       l.m_locator_id,
       l.m_locatorto_id,
       l.m_attributesetinstance_id,
       productattribute(l.m_attributesetinstance_id)   AS productattribute,
       l.m_attributesetinstanceto_id,
       productattribute(l.m_attributesetinstanceto_id) AS productattributeto,
       pasi.m_attributeset_id,
       l.c_uom_id,
       l.qtyentered,
       l.qtyordered,
       l.qtyreserved,
       l.qtydelivered,
       l.confirmedqty,
       l.qtyintransit,
       l.targetqty,
       l.qtyordered - l.qtydelivered                   AS qtytodeliver,
       l.description
FROM dd_order o
         JOIN dd_orderline l ON l.dd_order_id = o.dd_order_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN m_attributesetinstance pasi ON l.m_attributesetinstance_id = pasi.m_attributesetinstance_id
         LEFT JOIN m_attributesetinstance pasito ON l.m_attributesetinstanceto_id = pasito.m_attributesetinstance_id
;

