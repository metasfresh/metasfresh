DROP VIEW M_InOut_LineConfirm_vt
;

CREATE OR REPLACE VIEW M_InOut_LineConfirm_vt AS
SELECT iolc.ad_client_id,
       iolc.ad_org_id,
       iolc.isactive,
       iolc.created,
       iolc.createdby,
       iolc.updated,
       iolc.updatedby,
       uom.ad_language,
       iolc.m_inoutlineconfirm_id,
       iolc.m_inoutconfirm_id,
       iolc.targetqty,
       iolc.confirmedqty,
       iolc.differenceqty,
       iolc.scrappedqty,
       iolc.description,
       iolc.processed,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       iol.movementqty,
       uom.uomsymbol,
       ol.qtyordered - ol.qtydelivered                      AS qtybackordered,
       COALESCE(COALESCE(pt.name, p.name), iol.description) AS name,
       CASE
           WHEN COALESCE(pt.name, p.name) IS NOT NULL THEN iol.description
                                                      ELSE NULL::character varying
       END                                                  AS shipdescription,
       COALESCE(pt.documentnote, p.documentnote)            AS documentnote,
       p.upc,
       p.sku,
       p.value                                              AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id
FROM m_inoutlineconfirm iolc
         JOIN m_inoutline iol ON iolc.m_inoutline_id = iol.m_inoutline_id
         JOIN c_uom_trl uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_product_trl pt ON iol.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id
;

