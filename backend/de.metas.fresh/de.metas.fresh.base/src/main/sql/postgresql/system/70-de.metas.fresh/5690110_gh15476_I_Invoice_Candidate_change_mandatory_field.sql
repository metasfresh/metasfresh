-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2023-06-05T12:44:20.148997100Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-06-05 14:44:20.148','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584169
;

-- 2023-06-05T12:44:28.045679400Z
INSERT INTO t_alter_column values('i_invoice_candidate','Bill_User_ID','NUMERIC(10)',null,null)
;

-- 2023-06-05T12:44:28.051381400Z
INSERT INTO t_alter_column values('i_invoice_candidate','Bill_User_ID',null,'NULL',null)
;

