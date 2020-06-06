delete from m_hu_item_storage_snapshot where 1=1;
delete from m_hu_storage_snapshot where 1=1;
delete from m_hu_item_storage where 1=1;
delete from m_hu_storage where 1=1;
update m_hu set hustatus='D', isactive='N' where IsActive='Y';
