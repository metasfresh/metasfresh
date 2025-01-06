DROP VIEW RV_Storage;

CREATE OR REPLACE VIEW RV_Storage AS
SELECT s.ad_client_id,
       s.ad_org_id,
       s.m_product_id,
       p.value,
       p.name,
       p.description,
       p.upc,
       p.sku,
       p.c_uom_id,
       p.m_product_category_id,
       p.classification,
       p.weight,
       p.volume,
       p.versionno,
       p.guaranteedays,
       p.guaranteedaysmin,
       s.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       s.qtyonhand,
       s.qtyreserved,
       s.qtyonhand - s.qtyreserved AS qtyavailable,
       s.qtyordered,
       s.datelastinventory,
       s.m_attributesetinstance_id,
       asi.m_attributeset_id,
       NULL::numeric               AS shelflifedays,
       NULL::numeric               AS goodfordays,
       NULL::numeric
                                   AS shelfliferemainingpct
FROM m_storage s
         JOIN m_locator l ON s.m_locator_id = l.m_locator_id
         JOIN m_product p ON s.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON s.m_attributesetinstance_id = asi.m_attributesetinstance_id
;
