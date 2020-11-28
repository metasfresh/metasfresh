DROP VIEW m_subsequentdelivery_v;

CREATE OR REPLACE VIEW m_subsequentdelivery_v AS 
 SELECT sol.c_orderline_id AS m_subsequentdelivery_v_id,
    sol.ad_client_id,
    sol.ad_org_id,
    sol.created,
    sol.createdby,
    sol.updated,
    sol.updatedby,
    sol.c_orderline_id,
    o.documentno,
    o.datepromised,
    ppo.maxdeliverytime,
    ppo.mindeliverytime,
    sol.m_product_id,
    sol.qtyreserved
   FROM c_orderline sol
     LEFT JOIN c_order o ON o.c_order_id = sol.c_order_id
     LEFT JOIN m_product p ON p.m_product_id = sol.m_product_id
     LEFT JOIN ( SELECT m_product_po.m_product_id,
            max(
                CASE
                    WHEN m_product_po.deliverytime_actual IS NOT NULL AND m_product_po.deliverytime_actual <> 0::numeric THEN m_product_po.deliverytime_actual
                    ELSE m_product_po.deliverytime_promised
                END) AS maxdeliverytime,
            min(
                CASE
                    WHEN m_product_po.deliverytime_actual IS NOT NULL AND m_product_po.deliverytime_actual <> 0::numeric THEN m_product_po.deliverytime_actual
                    ELSE m_product_po.deliverytime_promised
                END) AS mindeliverytime
           FROM m_product_po
          GROUP BY m_product_po.m_product_id) ppo ON ppo.m_product_id = sol.m_product_id
  WHERE o.issotrx = 'Y'::bpchar AND sol.qtyreserved > 0::numeric AND sol.qtydelivered > 0::numeric
;

