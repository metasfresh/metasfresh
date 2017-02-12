
-- first delete stale/dangling log records
DELETE FROM C_Queue_WorkPackage_Log l WHERE NOT EXISTS (select 1 from C_Queue_WorkPackage p where p.C_Queue_WorkPackage_ID=l.C_Queue_WorkPackage_ID);

-- select * from db_columns_fk where tableName='C_Queue_WorkPackage_Log'
ALTER TABLE C_Queue_WorkPackage_Log DROP CONSTRAINT IF EXISTS CQueueWorkPackage_CQueueWorkPa;
ALTER TABLE C_Queue_WorkPackage_Log ADD CONSTRAINT CQueueWorkPackage_CQueueWorkPa FOREIGN KEY (C_Queue_WorkPackage_ID) REFERENCES C_Queue_WorkPackage ON DELETE CASCADE;

