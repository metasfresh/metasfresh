DROP VIEW IF EXISTS QtyDemand_QtySupply_V
;

CREATE OR REPLACE VIEW QtyDemand_QtySupply_V AS

SELECT coalesce(ss.ad_client_id, rs.ad_client_id)     as ad_client_id,
       coalesce(ss.ad_org_id, rs.ad_org_id)           as ad_org_id,
       coalesce(qtyReserved, 0)                  as qtyReserved,
       coalesce(qtyToMove, 0)                    as qtyToMove,
       coalesce(ss.m_product_id, rs.m_product_id)     as m_product_id,
       coalesce(ss.c_uom_id, rs.c_uom_id)             as c_uom_id,
       coalesce(ss.attributesKey, rs.attributesKey)   as attributesKey,
       coalesce(ss.m_warehouse_id, rs.m_warehouse_id) as m_warehouse_id

FROM (SELECT m_shipmentschedule.ad_client_id,
             m_shipmentschedule.ad_org_id,
             m_shipmentschedule.m_warehouse_id,
             sum(qtyReserved) as                                                           qtyReserved,
             m_shipmentschedule.m_product_id,
             generateasistorageattributeskey(m_shipmentschedule.m_attributesetinstance_id) attributesKey,
             product.c_uom_id
      FROM m_shipmentschedule
               INNER JOIN m_product product on m_shipmentschedule.m_product_id = product.m_product_id
      GROUP BY m_shipmentschedule.ad_client_id, m_shipmentschedule.ad_org_id, m_shipmentschedule.m_warehouse_id,
               m_shipmentschedule.m_product_id, attributesKey, product.c_uom_id) as ss
         FULL OUTER JOIN (SELECT ad_client_id,
                                 ad_org_id,
                                 m_warehouse_id,
                                 sum(qtyToMove) as                                          qtyToMove,
                                 m_product_id,
                                 generateasistorageattributeskey(m_attributesetinstance_id) attributesKey,
                                 c_uom_id
                          FROM m_receiptschedule
                          GROUP BY m_receiptschedule.ad_client_id, m_receiptschedule.ad_org_id,
                                   m_receiptschedule.m_product_id, m_receiptschedule.m_warehouse_id,
                                   attributesKey, c_uom_id) as rs
                         on rs.m_product_id = ss.m_product_id
                             and rs.ad_client_id = ss.ad_client_id
                             and rs.ad_org_id = ss.ad_org_id
                             and rs.m_warehouse_id = ss.m_warehouse_id
                             and rs.attributesKey::text = ss.attributesKey::text
                             and rs.c_uom_id = ss.c_uom_id;