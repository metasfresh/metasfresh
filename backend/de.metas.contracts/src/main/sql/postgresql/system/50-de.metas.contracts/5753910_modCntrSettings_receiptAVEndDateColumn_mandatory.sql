-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T09:17:35.805Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-05-08 11:17:35.805','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589958
;

-- 2025-05-08T09:17:39.545Z
INSERT INTO t_alter_column values('modcntr_settings','ReceiptAVEndDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2025-05-08T09:17:39.546Z
INSERT INTO t_alter_column values('modcntr_settings','ReceiptAVEndDate',null,'NOT NULL',null)
;

