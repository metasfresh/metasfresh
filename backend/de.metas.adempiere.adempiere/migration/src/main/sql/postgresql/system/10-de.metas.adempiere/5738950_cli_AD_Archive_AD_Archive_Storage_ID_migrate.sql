update ad_archive a set ad_archive_storage_id=(select ad_archive_storage_id
                                               from ad_archive_storage s 
                                               where s.type='filesystem' and s.ad_client_id=a.ad_client_id)
from backup.ad_archive_before_isfilesystem_drop t
where t.ad_archive_id=a.ad_archive_id
and t.isFileSystem='Y';

update ad_archive set ad_archive_storage_id=540000 where ad_archive_storage_id is null;

