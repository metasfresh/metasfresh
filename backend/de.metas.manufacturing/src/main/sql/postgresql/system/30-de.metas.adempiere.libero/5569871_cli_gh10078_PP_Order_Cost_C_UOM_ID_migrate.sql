-- SELECT bl.pp_order_id, bl.m_product_id, count(1)
-- FROM pp_order_bomline bl
-- GROUP BY bl.pp_order_id, bl.m_product_id
-- HAVING count(1) > 1
-- ;


UPDATE pp_order_cost oc
SET c_uom_id=(SELECT bl.c_uom_id
              FROM pp_order_bomline bl
              WHERE bl.pp_order_id = oc.pp_order_id
                AND bl.m_product_id = oc.m_product_id
              ORDER BY bl.pp_order_bomline_id ASC
              LIMIT 1)
WHERE c_uom_id IS NULL
;

UPDATE pp_order_cost oc
SET c_uom_id=(SELECT mo.c_uom_id FROM pp_order mo WHERE mo.pp_order_id = oc.pp_order_id AND mo.m_product_id = oc.m_product_id)
WHERE c_uom_id IS NULL
;

UPDATE pp_order_cost oc
SET c_uom_id=(SELECT distinct n.c_uom_id
              FROM m_product p
                       INNER JOIN pp_order_node n ON n.pp_order_id = oc.pp_order_id AND n.s_resource_id = p.s_resource_id)
WHERE c_uom_id IS NULL
;

-- select * from pp_order_cost where c_uom_id is null;

