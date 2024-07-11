
ALTER TABLE IF EXISTS dlm.C_Queue_Workpackage_Log_Archived
    ADD COLUMN ad_issue_id numeric(10,0);

alter table dlm.C_Queue_Workpackage_Archived
    add batchenqueuedcount numeric(10) default NULL::numeric;
