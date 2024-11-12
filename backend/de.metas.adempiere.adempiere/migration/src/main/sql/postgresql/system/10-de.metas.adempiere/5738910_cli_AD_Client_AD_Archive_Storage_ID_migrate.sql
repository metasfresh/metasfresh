drop table if exists tmp_storage_to_create;
create temporary table tmp_storage_to_create as
select ad_client_id, storearchiveonfilesystem, unixarchivepath, windowsarchivepath, nextval('ad_archive_storage_seq') as ad_archive_storage_id from ad_client where storearchiveonfilesystem='Y';

insert into ad_archive_storage (ad_archive_storage_id, ad_client_id, ad_org_id, created, createdby, description, isactive, path, type, updated, updatedby) 
select t.ad_archive_storage_id, t.ad_client_id, 0 as ad_org_id, now(), 0, '', 'Y', unixarchivepath as path, 'filesystem' as type, now(), 0
    from tmp_storage_to_create t;

update ad_client c set ad_archive_storage_id=t.ad_archive_storage_id
from tmp_storage_to_create t
where t.ad_client_id=c.ad_client_id;

update ad_client set ad_archive_storage_id=540000 where ad_archive_storage_id is null;

-- select ad_client_id, ad_client.ad_archive_storage_id, storearchiveonfilesystem, unixarchivepath, windowsarchivepath from ad_client order by ad_client_id

