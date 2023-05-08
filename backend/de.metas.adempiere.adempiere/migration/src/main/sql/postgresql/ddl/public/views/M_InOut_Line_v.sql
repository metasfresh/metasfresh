DROP VIEW M_InOut_Line_v;

CREATE OR REPLACE VIEW M_InOut_Line_v AS
SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       'de_DE'::text                                                                                                        AS ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.movementqty
                                                                              ELSE NULL::numeric
       END                                                                                                                  AS movementqty,
       CASE
           WHEN iol.qtyentered <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.qtyentered
                                                                             ELSE NULL::numeric
       END                                                                                                                  AS qtyentered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN uom.uomsymbol
                                                                              ELSE NULL::character varying
       END                                                                                                                  AS uomsymbol,
       ol.qtyordered,
       ol.qtydelivered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN ol.qtyordered - ol.qtydelivered
                                                                              ELSE NULL::numeric
       END                                                                                                                  AS qtybackordered,
       COALESCE(p.name::text || productattribute(iol.m_attributesetinstance_id)::text, c.name::text, iol.description::text) AS name,
       CASE
           WHEN COALESCE(c.name, p.name) IS NOT NULL THEN iol.description
                                                     ELSE NULL::character varying
       END                                                                                                                  AS description,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                                                                                                              AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                                                                                                        AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM m_inoutline iol
         JOIN c_uom uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_charge c ON iol.c_charge_id = c.c_charge_id
UNION
SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       'de_DE'::text                     AS ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line + bl.line / 100::numeric AS line,
       p.m_product_id,
       CASE
           WHEN bl.isqtypercentage = 'N'::bpchar THEN iol.movementqty * bl.qtybom
                                                 ELSE iol.movementqty * (bl.qtybatch / 100::numeric)
       END                               AS movementqty,
       CASE
           WHEN bl.isqtypercentage = 'N'::bpchar THEN iol.qtyentered * bl.qtybom
                                                 ELSE iol.qtyentered * (bl.qtybatch / 100::numeric)
       END                               AS qtyentered,
       uom.uomsymbol,
       NULL::numeric                     AS qtyordered,
       NULL::numeric                     AS qtydelivered,
       NULL::numeric                     AS qtybackordered,
       p.name,
       b.description,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                           AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                     AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM pp_product_bom b
         JOIN m_inoutline iol ON b.m_product_id = iol.m_product_id
         JOIN m_product bp ON bp.m_product_id = iol.m_product_id AND bp.isbom = 'Y'::bpchar AND bp.isverified = 'Y'::bpchar AND bp.ispicklistprintdetails = 'Y'::bpchar
         JOIN pp_product_bomline bl ON bl.pp_product_bom_id = b.pp_product_bom_id
         JOIN m_product p ON bl.m_product_id = p.m_product_id
         JOIN c_uom uom ON p.c_uom_id = uom.c_uom_id
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id;
