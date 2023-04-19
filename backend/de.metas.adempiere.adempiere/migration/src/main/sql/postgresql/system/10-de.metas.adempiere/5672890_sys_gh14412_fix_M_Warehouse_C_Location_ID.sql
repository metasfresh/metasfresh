
create table backup.m_warehouse_20230123_bkp as select * from m_warehouse;

create table backup.m_warehouse_20230123_fix_c_locations as
select
    bpl.c_location_id as c_location_id_new,
    wh.c_location_id as c_location_id_old,
    wh.m_warehouse_id
from m_warehouse wh join c_Bpartner_location bpl on bpl.c_Bpartner_location_ID=wh.c_Bpartner_location_id
where wh.c_location_id!=bpl.c_location_id;

update m_warehouse wh set updated='2023-01-23 07:31', updatedby=99, c_location_id=fix.c_location_id_new
from backup.m_warehouse_20230123_fix_c_locations fix where fix.m_warehouse_id=wh.m_warehouse_id;
