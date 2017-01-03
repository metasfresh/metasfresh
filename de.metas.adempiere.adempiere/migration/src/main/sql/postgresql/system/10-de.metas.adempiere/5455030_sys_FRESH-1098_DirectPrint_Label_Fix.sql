-- 03.01.2017 16:11
-- URL zum Konzept
update ad_process set isdirectprint = 'Y' where ad_process_id in
(
--select p.ad_process_id
select p.ad_process_id, p.isdirectprint
from AD_Table_Process tp
left join ad_process p on tp.ad_process_id = p.ad_process_id
where true
and tp.ad_table_id = (select ad_table_id from ad_table where tablename = 'M_HU')
and tp.entitytype = 'de.metas.fresh'
)
;
