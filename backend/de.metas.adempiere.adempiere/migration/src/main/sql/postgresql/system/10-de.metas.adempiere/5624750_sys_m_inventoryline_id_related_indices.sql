drop index if exists m_inventoryline_hu$m_inventoryline_id;
create index m_inventoryline_hu$m_inventoryline_id on m_inventoryline_hu(m_inventoryline_id);

drop index if exists m_transaction$m_inventoryline_id;
create index m_transaction$m_inventoryline_id on m_transaction(m_inventoryline_id);

