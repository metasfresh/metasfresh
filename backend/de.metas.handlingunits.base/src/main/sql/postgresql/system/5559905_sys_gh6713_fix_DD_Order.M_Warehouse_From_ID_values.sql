

-- set correct values
UPDATE dd_order
SET m_warehouse_from_id = (
    SELECT w.m_warehouse_id
    FROM m_warehouse w
             INNER JOIN m_locator ml ON w.m_warehouse_id = ml.m_warehouse_id
             INNER JOIN dd_orderline d ON ml.m_locator_id = d.m_locator_id
    WHERE d.dd_order_id = dd_order.dd_order_id
    LIMIT 1)
;

