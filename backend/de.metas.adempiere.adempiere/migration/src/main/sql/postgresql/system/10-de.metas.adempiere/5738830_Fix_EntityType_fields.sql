drop table if exists tmp_column_to_update;
create temporary table tmp_column_to_update as
select t.tablename, c.columnname,
       (select r.name from ad_reference r where r.ad_reference_id=c.ad_reference_id) as ref,
       (select r.name from ad_reference r where r.ad_reference_id=c.ad_reference_value_id) as ref_value,
       c.ad_reference_id,
       c.ad_column_id
from ad_column c
inner join ad_table t on t.ad_table_id=c.ad_table_id
where c.columnname='EntityType'
and c.ad_reference_id=30
order by t.tablename;
-- select * from tmp_column_to_update;

update ad_column c set ad_reference_id=18 -- table
from tmp_column_to_update t
where t.ad_column_id=c.ad_column_id;
