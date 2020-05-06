update c_campaign_price cp set c_uom_id=(select p.c_uom_id from m_product p where p.m_product_id=cp.m_product_id)
where cp.c_uom_id is null;

