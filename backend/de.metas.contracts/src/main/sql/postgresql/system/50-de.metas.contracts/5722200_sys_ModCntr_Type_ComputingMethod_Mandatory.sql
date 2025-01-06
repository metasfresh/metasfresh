

-- Column: ModCntr_Type.ModularContractHandlerType
-- 2024-04-17T14:08:07.678Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-17 17:08:07.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587541
;

-- 2024-04-17T14:08:08.809Z
INSERT INTO t_alter_column values('modcntr_type','ModularContractHandlerType','VARCHAR(255)',null,null)
;

-- 2024-04-17T14:08:08.813Z
INSERT INTO t_alter_column values('modcntr_type','ModularContractHandlerType',null,'NOT NULL',null)
;

