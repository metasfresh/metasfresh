DROP VIEW IF EXISTS QtyDemand_QtySupply_V
;

CREATE OR REPLACE VIEW QtyDemand_QtySupply_V AS

SELECT COALESCE(sched.ad_client_id, rs.ad_client_id)     AS ad_client_id,
       COALESCE(sched.ad_org_id, rs.ad_org_id)           AS ad_org_id,
       COALESCE(qtyReserved, 0)                          AS qtyReserved,
       COALESCE(qtyToMove, 0)                            AS qtyToMove,
       COALESCE(sched.m_product_id, rs.m_product_id)     AS m_product_id,
       COALESCE(sched.c_uom_id, rs.c_uom_id)             AS c_uom_id,
       COALESCE(sched.attributesKey, rs.attributesKey)   AS attributesKey,
       COALESCE(sched.m_warehouse_id, rs.m_warehouse_id) AS m_warehouse_id

FROM (SELECT m_shipmentschedule.ad_client_id,
             m_shipmentschedule.ad_org_id,
             m_shipmentschedule.m_warehouse_id,

             -- qtyReserved is in the product's UOM and does not need to be converted
             SUM(qtyReserved) AS                                                           qtyReserved,

             m_shipmentschedule.m_product_id,
             generateasistorageattributeskey(m_shipmentschedule.m_attributesetinstance_id) attributesKey,
             product.c_uom_id
      FROM m_shipmentschedule
               INNER JOIN m_product product ON m_shipmentschedule.m_product_id = product.m_product_id
      GROUP BY m_shipmentschedule.ad_client_id, m_shipmentschedule.ad_org_id, m_shipmentschedule.m_warehouse_id,
               m_shipmentschedule.m_product_id, attributesKey, product.c_uom_id) AS sched
         FULL OUTER JOIN (SELECT rs.ad_client_id,
                                 rs.ad_org_id,
                                 rs.m_warehouse_id,

                                 -- make sure that m_receiptschedule.qtyToMove is also in the product's UOM
                                 SUM(uomconvert(rs.m_product_id, rs.c_uom_id, product.c_uom_id, rs.qtyToMove)) AS qtyToMove,

                                 rs.m_product_id,
                                 generateasistorageattributeskey(rs.m_attributesetinstance_id)                    attributesKey,
                                 product.c_uom_id
                          FROM m_receiptschedule rs
                                   INNER JOIN m_product product ON rs.m_product_id = product.m_product_id
                          GROUP BY rs.ad_client_id, rs.ad_org_id,
                                   rs.m_product_id, rs.m_warehouse_id,
                                   attributesKey,
                                   product.c_uom_id) AS rs
                         ON rs.m_product_id = sched.m_product_id
                             AND rs.ad_client_id = sched.ad_client_id
                             AND rs.ad_org_id = sched.ad_org_id
                             AND rs.m_warehouse_id = sched.m_warehouse_id
                             AND rs.attributesKey::text = sched.attributesKey::text
                             AND rs.c_uom_id = sched.c_uom_id
;