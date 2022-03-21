-- Column: C_Queue_WorkPackage.C_Queue_PackageProcessor_ID
-- 2022-02-04T10:16:30.774Z
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2022-02-04 12:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550199
;

-- 2022-02-04T10:16:32.025Z
/* DDL */ SELECT public.db_alter_table('C_Queue_WorkPackage','ALTER TABLE public.C_Queue_WorkPackage ADD COLUMN C_Queue_PackageProcessor_ID NUMERIC(10)')
;

-- 2022-02-04T10:16:32.329Z
ALTER TABLE C_Queue_WorkPackage ADD CONSTRAINT CQueuePackageProcessor_CQueueWorkPackage FOREIGN KEY (C_Queue_PackageProcessor_ID) REFERENCES public.C_Queue_PackageProcessor DEFERRABLE INITIALLY DEFERRED
;

alter table dlm.c_queue_workpackage_archived ADD c_queue_packageprocessor_id NUMERIC(10) DEFAULT NULL
;

