update c_order o set m_warehouse_id=(select coalesce(oi.m_warehousepo_id, oi.m_warehouse_id, oi.dropship_warehouse_id) from ad_orginfo oi where oi.ad_org_id=o.ad_org_id)
where issotrx='N'
and m_warehouse_id is null;

