-- Column: ModCntr_Log.ContractType
-- 2023-08-14T15:55:50.099562800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-08-14 18:55:50.099','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587273
;

-- 2023-08-14T15:55:53.436231800Z
INSERT INTO t_alter_column values('modcntr_log','ContractType','VARCHAR(250)',null,null)
;

-- 2023-08-14T15:55:53.444596Z
INSERT INTO t_alter_column values('modcntr_log','ContractType',null,'NOT NULL',null)
;

