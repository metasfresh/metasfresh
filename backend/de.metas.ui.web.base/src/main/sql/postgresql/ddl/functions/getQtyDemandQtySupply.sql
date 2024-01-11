drop function getQtyDemandQtySupply;
CREATE OR REPLACE FUNCTION getQtyDemandQtySupply(
    p_M_Product_IDs numeric[]
)
    RETURNS table
            (
                ad_client_id numeric(10),
                ad_org_id numeric(10),
                qtyReserved numeric,
                qtyToMove numeric,
                m_product_id numeric(10),
                c_uom_id numeric(10),
                attributesKey text,
                m_warehouse_id numeric(10)
            )
AS
$BODY$
BEGIN
    RAISE NOTICE 'p_M_Product_IDs: %', p_M_Product_IDs;
    IF (p_M_Product_IDs IS NULL OR CARDINALITY(p_M_Product_IDs) <= 0) THEN
        RAISE EXCEPTION 'At least one M_Product_ID shall be specified';
    END IF;

    RETURN QUERY
        SELECT COALESCE(sched.ad_client_id, rs.ad_client_id)     AS ad_client_id,
               COALESCE(sched.ad_org_id, rs.ad_org_id)           AS ad_org_id,
               COALESCE(sched.qtyReserved, 0)                    AS qtyReserved,
               COALESCE(rs.qtyToMove, 0)                         AS qtyToMove,
               COALESCE(sched.m_product_id, rs.m_product_id)     AS m_product_id,
               COALESCE(sched.c_uom_id, rs.c_uom_id)             AS c_uom_id,
               COALESCE(sched.attributesKey, rs.attributesKey)   AS attributesKey,
               COALESCE(sched.m_warehouse_id, rs.m_warehouse_id) AS m_warehouse_id
        FROM (SELECT m_shipmentschedule.ad_client_id,
                     m_shipmentschedule.ad_org_id,
                     m_shipmentschedule.m_warehouse_id,

                     -- qtyReserved is in the product's UOM and does not need to be converted
                     SUM(m_shipmentschedule.qtyReserved) AS                                                           qtyReserved,

                     m_shipmentschedule.m_product_id,
                     generateasistorageattributeskey(m_shipmentschedule.m_attributesetinstance_id) attributesKey,
                     product.c_uom_id
              FROM m_shipmentschedule
                       INNER JOIN m_product product ON m_shipmentschedule.m_product_id = product.m_product_id
              WHERE coalesce(m_shipmentschedule.qtyReserved, 0) <> 0 and product.m_product_id = ANY (p_M_Product_IDs)
              GROUP BY m_shipmentschedule.ad_client_id, m_shipmentschedule.ad_org_id, m_shipmentschedule.m_warehouse_id,
                       m_shipmentschedule.m_product_id, attributesKey, product.c_uom_id) AS sched
                 FULL OUTER JOIN
             (SELECT rs.ad_client_id,
                     rs.ad_org_id,
                     rs.m_warehouse_id,

                     -- make sure that m_receiptschedule.qtyToMove is also in the product's UOM
                     SUM(uomconvert(rs.m_product_id, rs.c_uom_id, product.c_uom_id, rs.qtyToMove)) AS qtyToMove,

                     rs.m_product_id,
                     generateasistorageattributeskey(rs.m_attributesetinstance_id) AS attributesKey,
                     product.c_uom_id
              FROM m_receiptschedule rs
                       INNER JOIN m_product product ON rs.m_product_id = product.m_product_id
              WHERE coalesce(rs.qtyToMove, 0) <> 0 and product.m_product_id = ANY (p_M_Product_IDs)
              GROUP BY rs.ad_client_id, rs.ad_org_id, rs.m_product_id,
                       rs.m_warehouse_id, attributesKey,product.c_uom_id) AS rs
             ON rs.m_product_id = sched.m_product_id
                 AND rs.ad_client_id = sched.ad_client_id
                 AND rs.ad_org_id = sched.ad_org_id
                 AND rs.m_warehouse_id = sched.m_warehouse_id
                 AND rs.attributesKey::text = sched.attributesKey::text
                 AND rs.c_uom_id = sched.c_uom_id;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;
