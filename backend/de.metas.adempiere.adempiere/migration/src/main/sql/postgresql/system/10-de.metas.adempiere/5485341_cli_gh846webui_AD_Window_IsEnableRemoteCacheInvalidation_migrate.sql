update AD_Window w set IsEnableRemoteCacheInvalidation='N';

update AD_Window w set IsEnableRemoteCacheInvalidation='Y'
where w.IsActive='Y'
and exists (select 1 from AD_menu m where m.AD_Window_ID=w.AD_Window_ID and m.IsActive='Y')
and exists (
	select 1 from AD_Tab tt
	inner join AD_Table t on (t.AD_Table_ID=tt.AD_Table_ID)
	where tt.AD_Window_ID=w.AD_Window_ID and tt.SeqNo=10 and t.TableName<>'C_Print_Package'
)
;
