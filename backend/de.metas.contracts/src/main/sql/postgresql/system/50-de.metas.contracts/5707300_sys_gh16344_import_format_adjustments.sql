-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-16T17:19:45.053Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-16 18:19:45.052','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587544
;

-- 2023-10-16T17:20:07.704Z
INSERT INTO t_alter_column values('i_modcntr_log','DateTrx','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T17:20:07.713Z
INSERT INTO t_alter_column values('i_modcntr_log','DateTrx',null,'NOT NULL',null)
;
