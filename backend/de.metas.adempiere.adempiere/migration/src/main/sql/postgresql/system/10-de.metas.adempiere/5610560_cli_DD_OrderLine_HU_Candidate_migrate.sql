UPDATE dd_orderline_hu_candidate c
SET m_product_id=l.m_product_id,
    pickfrom_locator_id=l.m_locator_id,
    pickfrom_warehouse_id=(SELECT loc.m_warehouse_id FROM m_locator loc WHERE loc.m_locator_id = l.m_locator_id),
    dropto_locator_id=l.m_locatorto_id,
    dropto_warehouse_id=(SELECT loc.m_warehouse_id FROM m_locator loc WHERE loc.m_locator_id = l.m_locatorto_id)
FROM dd_orderline l
WHERE l.dd_orderline_id = c.dd_orderline_id
;

