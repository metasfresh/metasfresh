
-- M_Source_HUs need to be removed when the respective HU is issues to an pp_order_qty.
-- this SQL cleans up old records
create table backup.m_source_hu_20230202_2 as select * from m_source_hu;
delete from m_source_hu where m_hu_id in (select m_hu_id from pp_order_qty c );
