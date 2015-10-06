-- select * from db_columns_fk where tableName='C_Queue_WorkPackage_Log'
ALTER TABLE C_Queue_WorkPackage_Log DROP CONSTRAINT IF EXISTS CQueueWorkPackage_CQueueWorkPa;
ALTER TABLE C_Queue_WorkPackage_Log ADD CONSTRAINT CQueueWorkPackage_CQueueWorkPa FOREIGN KEY (C_Queue_WorkPackage_ID) REFERENCES C_Queue_WorkPackage ON DELETE CASCADE;

