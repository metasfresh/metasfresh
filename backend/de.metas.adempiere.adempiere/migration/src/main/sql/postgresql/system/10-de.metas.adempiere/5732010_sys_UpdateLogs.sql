create table backup.C_Doc_Outbound_Log as select * from C_Doc_Outbound_Log;

update C_Doc_Outbound_Log  set ad_archive_id = a.ad_archive_id
from AD_Archive a where a.ad_table_id=C_Doc_Outbound_Log.ad_table_id and a.record_id=C_Doc_Outbound_Log.record_id
and a.c_bpartner_id=C_Doc_Outbound_Log.c_bpartner_id;