-- Column: DATEV_ExportLine.CR_Account
-- Column: DATEV_ExportLine.CR_Account
-- 2024-12-12T14:13:25.504Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-12-12 16:13:25.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=559148
;

-- 2024-12-12T14:13:29.627Z
INSERT INTO t_alter_column values('datev_exportline','CR_Account','VARCHAR(40)',null,null)
;

-- 2024-12-12T14:13:29.663Z
INSERT INTO t_alter_column values('datev_exportline','CR_Account',null,'NULL',null)
;

-- Column: DATEV_ExportLine.DR_Account
-- Column: DATEV_ExportLine.DR_Account
-- 2024-12-12T14:13:44.302Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-12-12 16:13:44.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=559147
;

-- 2024-12-12T14:13:47.944Z
INSERT INTO t_alter_column values('datev_exportline','DR_Account','VARCHAR(40)',null,null)
;

-- 2024-12-12T14:13:47.975Z
INSERT INTO t_alter_column values('datev_exportline','DR_Account',null,'NULL',null)
;

