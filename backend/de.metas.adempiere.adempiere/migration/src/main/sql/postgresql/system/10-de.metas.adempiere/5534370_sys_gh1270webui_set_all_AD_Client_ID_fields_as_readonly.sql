drop table if exists backup.ad_field_clients_not_readonly_gh1270webui;

create table backup.ad_field_clients_not_readonly_gh1270webui as
select f.ad_field_id
from ad_field f
where exists (select 1 from ad_column c where c.ad_column_id=f.ad_column_id and c.ColumnName='AD_Client_ID')
and f.isreadonly<>'Y';

update ad_field f set IsReadOnly='Y'
where f.ad_field_id in (select t.ad_field_id from backup.ad_field_clients_not_readonly_gh1270webui t);

