-- backup
create table backup.c_dataimport_gh5005 AS
select * from c_dataimport;

create table backup.ad_impformat_row_gh5005 AS
select * from ad_impformat_row;

create table backup.ad_impformat_gh5005 AS
select * from ad_impformat;

-- delete possible existent data
DELETE FROM c_dataimport WHERE exists (select 1 from ad_impformat f where f.ad_impformat_id = c_dataimport.ad_impformat_id and  ad_impformat_id = 1000001 AND ad_table_ID = 534);
DELETE FROM ad_impformat_row WHERE exists (select 1 from ad_impformat f where f.ad_impformat_id = ad_impformat_row.ad_impformat_id and  ad_impformat_id = 1000001 AND ad_table_ID = 534);
DELETE FROM ad_impformat WHERE ad_impformat_id = 1000001 AND ad_table_ID = 534;