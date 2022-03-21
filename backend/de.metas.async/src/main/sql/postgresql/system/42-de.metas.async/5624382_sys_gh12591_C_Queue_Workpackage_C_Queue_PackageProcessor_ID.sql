-- Column: C_Queue_WorkPackage.C_Queue_PackageProcessor_ID
-- 2022-02-22T22:55:20.101Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-23 00:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550199
;

-- 2022-02-22T22:55:24.051Z
INSERT INTO t_alter_column values('c_queue_workpackage','C_Queue_PackageProcessor_ID','NUMERIC(10)',null,null)
;

-- 2022-02-22T22:55:24.061Z
INSERT INTO t_alter_column values('c_queue_workpackage','C_Queue_PackageProcessor_ID',null,'NOT NULL',null)
;

alter table dlm.c_queue_workpackage_archived alter column c_queue_packageprocessor_id set not null
;