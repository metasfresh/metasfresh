-- Column: GPLR_Report.Purchase_Invoice_ID
-- 2023-06-28T12:09:19.450Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-06-28 15:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587013
;

-- 2023-06-28T12:09:22.184Z
INSERT INTO t_alter_column values('gplr_report','Purchase_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2023-06-28T12:09:22.187Z
INSERT INTO t_alter_column values('gplr_report','Purchase_Invoice_ID',null,'NULL',null)
;

