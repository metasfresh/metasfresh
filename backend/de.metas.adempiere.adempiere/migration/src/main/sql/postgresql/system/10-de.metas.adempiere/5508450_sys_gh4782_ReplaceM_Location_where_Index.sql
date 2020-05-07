drop index m_location_where;
create unique index m_location_where on m_locator (m_warehouse_id, value, x, y, z, x1);